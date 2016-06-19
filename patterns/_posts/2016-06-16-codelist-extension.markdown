---
layout:            pattern
title:             "INSPIRE Class Extension Pattern: Codelist Extension"
author:            "Thorsten Reitz"
date:              2016-06-16 09:30:00 +0200
category:          patterns
tags:              [pattern, property]
patternName:       "Codelist Extension"
patternType:       property
patternIdentifier: codelist

---

## Intent

In data modelling, we use code lists as a constraint to ensure consistent data with a clearly defined meaning. A code list defines permitted values for a property. Often, we encode the semantic label in a value that is easy to store and query for, such as `1000`, where we need to look up `1000` to see that is refers to a `Building`.

A relatively easy way to extend an INSPIRE Data Specification is to modify or substitute the numerous code lists that come with INSPIRE. <a href="http://inspire.ec.europa.eu/codelist" target="_blank">INSPIRE code lists</a> have been designed with extensibility in mind; each code list explicitly announces whether you are allowed to extend it:

1. A code list is not extensible (`none`)
1. A code list is extensible using narrower values (`narrower`)
1. The list is extensible using additional values at any level (`open`)
1. Any values are allowed (`any`)

What exactly an individual code list allows is defined in its extensibility element:

<pre class="line-numbers">
<code class="language-xml">
  <extensibility id="http://inspire.ec.europa.eu/registry/extensibility/open">
    <uriname>open</uriname>
  </extensibility>
</code>
</pre>

When the code list you need to modify permits extension of any type and there is a type or classification property on the class you need to extend, you can often use code list extension instead of inheritance to create new subtypes. This helps to keep the number of structurally similar classes down and helps with general interoperability.

## Structure

In UML, we indicate usage of an extended code list by substituting the existing code list. No new subtype of the class that has the property using the code list is necessary in this case. The code list itself is tagged with the GML stereotype `codelist`. 

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/codelist.png" class="figure-img img-fluid img-rounded" title="Code List Extension">
    <figcaption class="figure-caption small"><code>UrbanDesignationType</code> extends and substitutes <code>DesignationType</code>.</figcaption>
</figure>

Alternatively, you might want to give a stronger indication that the substitution code list needs to be used. In that case, you can either define a constraint or create a subtype that **redefines** the property (`siteDesignation`) in the example to use the extended code list instead. This is conceptually acceptable, since the extended code is a subtype of the original code list. 

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/codelist-redefine.png" class="figure-img img-fluid img-rounded" title="Code List Extension">
    <figcaption class="figure-caption small">An explicit reference to the extended <code>UrbanDesignationType</code> code list through subtyping of <code>ProtectedSite</code>and redefining the <code>siteDesignation</code>.
    .</figcaption>
</figure>

<table class="alert-warning important-info">
    <tr>
        <td style="width:3em"><div class="important-info-icon"><span class="glyphicon glyphicon-exclamation-sign" style="font-size:2em"></span></div></td>
        <td>Please note that **redefine** is only available in UML 2.0, and that tool support might be limited.</td>
    </tr>
</table>

## When to use

When you can satisfy your requirement by allowing additional values in an existing property, using a code list extension may be the easiest solution. So, extending a code list is a viable pattern in these cases:

1. When the values you want to add describe the same dimension or property of the objects, e.g. by adding a new kind of Building
1. When the basic type of the property (such as "String" or "Integer") doesn't need to be changed
1. When you have many semantic subtypes that have an identical structure, but different meanings

## When not to use

Code List extension is limited in scope, so there are many scenarios where it's not sufficient. There are are also some special cases you should pay attention to:

1. When there is no infrastructure to publish the extended code list, as code lists normally need to be registered
1. When the code list doesn't permit extension (`none`)
1. When the values you are adding don't describe the same property as the existing values
 
## XML Schema Example

Since a GML 3.3 Application Schema encodes code list values using a `gml:ReferenceType`, there is no direct reference to either the extended code list or the new subtype. The GML Application schema doesn't need to be changed to allow usage of the extended code list. For the codelist itself, there is no mandated encoding. In INSPIRE, we recommend a specific code list format defined as part of the data specifications.

What needs to be changed is the code list itself. You can see the code list as an addendum to the schema that defines allowed values.

<pre class="line-numbers" data-src="/patterns/examples/codelist-extended.xml">
<code class="language-xml">
</code>
</pre>

In this example, we take the `BuildingNature` code list published in the INSPIRE registry and add two new values for single-family residential houses and for multi-family residential houses. Any original values in the code list have to be left in, but are omitted in the example for brevity.

## XML Instance Example

In INSPIRE GML 3.3, we encode an instance of a code list value by using a `gml:ReferenceType`. In the `ReferenceType` element, we set the `xlink:href` attribute to point to the fully qualified name of the code list and the value. In addition, the INSPIRE guidelines recommend using the `xlink:title` attribute to a meaningful label.

<pre data-line="23,24" class="line-numbers" data-src="/patterns/examples/codelist-instance.xml">
<code class="language-xml">
</code>
</pre>

In line 23, we use `xlink:href` to link to the complete, qualified and resolvable value definition of `singleFamilyResidential`. We also add a readable title by means of the `xlink:title` attribute in line 24.

## Implementation Considerations

This section provides information when and how this pattern can be implemented on different types of platforms.

### Storage Backend

In a relational or document-oriented storage backend, we can store the coded value itself well. One potential issue lies in the character of URLs, which can be long strings. This can slightly impact performance when accessing data via these references.

### Download Services

This pattern can be implemented on XML-based platforms without special considerations.

### Business Logic

This pattern can be implemented on object-oriented platforms without special considerations. Using a narrower type in place of a more generic type fo a property is possible on all object-oriented, statically typed platforms.

### Consumer Side

There are several problems you might encounter when you use data with INSPIRE code list references. Often, we use code list values to drive symbology, and many client applications have problems with using URLs to drive symbology. Another issue is that clients can't directly use code lists that are hierarchical in structure, as most applications expect a true list, not a taxonomy. 


