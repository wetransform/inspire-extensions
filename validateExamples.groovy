#!/usr/bin/env groovy

import org.xml.sax.ErrorHandler
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import static groovy.io.FileType.*
import static groovy.io.FileVisitResult.*

// helpers

List findProblems(File xml, List<URL> xsds) {
  Source[] sources = new Source[xsds.size()];
  xsds.eachWithIndex { xsd, i ->
    sources[i] = new StreamSource(xsd.newInputStream())
  }

  SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI)
               .newSchema(sources)
               .newValidator().with { validator ->
    List exceptions = []
    Closure<Void> handler = { exception -> exceptions << exception }
    errorHandler = [ warning: handler, fatalError: handler, error: handler ] as ErrorHandler
    validate(new StreamSource(xml))
    exceptions
  }
}

List<URL> identifySchemas(File xml) {
  def locations = new XmlSlurper().parse(xml).'@xsi:schemaLocation' as String
  def result = []
  locations.tokenize().eachWithIndex { loc, index ->
    // only every second string is a location (the others are namespaces)
    if (index % 2 == 1) {
      try {
        result << new URL(loc)
      } catch (e) {
        result << new File(xml.parent, loc).toURI().toURL()
      }
    }
  }

  println "Identified the following schemas for validation: $result"

  result
}

ok = true

boolean validateFile(File xml) {
  // validate
  println "Validating $xml against XML Schema..."
  def problems = findProblems(xml, identifySchemas(xml))
  problems.each {
    println "Problem @ line $it.lineNumber, col $it.columnNumber : $it.message"
  }
  if (problems.size() > 0) {
    println "\nOverall ${problems.size()} errors ($xml)."
  }
  else {
    println "Validation successful ($xml)."
    return true
  }

  ok = false
  return false
}

def validate(File dir) {
  dir.traverse(
    type: FILES,
    nameFilter: ~/.*\.xml/
    ) {
    validateFile(it)
  }
}


// script

validate(new File('patterns/examples'))



// fail script if any validation failed

if (!ok) {
  System.exit(1)
}
