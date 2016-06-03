---
layout:            pattern
title:             "Inheritance"
author:            "Thorsten Reitz"
date:              2016-04-09 09:10:59 +0200
category:          patterns
tags:              [pattern, class]
patternName:       "Inheritance"
patternType:       class
patternIdentifier: inheritance

---

## Intent

Inheritance is a mechanism for reuse of software components, be it structure or behaviour. In object-oriented designs such as INSPIRE, inheritance is when a child class is based on a parent class. The child class inherits all the properties and methods from the parent class, which makes the child class usually interoperable to the target class. A child class can also override behaviour and in some cases structure of the parent class, making it at least partially incompatible to the parent class.

Inheritance establishes a clear hierarchy, which can be many levels deep. The parent class can have a grand-parent class, which can have a great-grand-parent class. All parent and further ancestors together are called *superclasses* of a class, while all children and grandchildren are called *subclasses*. In INSPIRE GML, we have inheritance hierarchies up to eight levels deep. As a consequence, when we create a new class that inherits from an INSPIRE class, it wil inherit every property defined on one of those superclasses.

## When to use

Inheritance is an easy way to achieve compatibility of your new classes with the respective INSPIRE classes. Your data will have additional information that goes beyond what INSPIRE mandates, which is not forbidden. Inheritance is particularly useful when...

1. ...you want to use one system for managing and publishing INSPIRE data and your extended data
1. ...there is not just a syntactic relationship, but also a semantic relationship between the child and the parent. This relationship is called a *subtyping* relationship and is usually read as *child* ```is-a``` *parent*.
1. ...you want to automatically keep your model compatible when superclasses change

Inheritance should not be confused with subtyping. In some languages inheritance and subtyping agree,[a] whereas in others they differ; in general subtyping establishes an is-a relationship, whereas inheritance only reuses implementation and establishes a syntactic relationship, not necessarily a semantic relationship

## When not to use

Inheritance is a static relationship type with strong coupling. Many of the reasons not to use inheritance derive from that strong coupling:

1. You don't want to bring in complexity inherited from a large set of superclasses
1. You don't want to inherit any changes from the imported superclasses
1. There is no ```is-a``` relationship between your class and the INSPIRE class
1. Your class should have an equal relationship with multiple INSPIRE classes (or other classes from a local standard)

## Structure

In this pattern, there is a parent class and a child class. They are connected through a Generalisation association which points from the child to the parent:

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/inheritance.png" class="figure-img img-fluid img-rounded" title="A matching table">
    <figcaption class="figure-caption small"><code>JoinedParcel</code> inherits all properties from <code>CadastralParcel</code> and adds a <code>joinedFrom</code> property.</figcaption>
</figure>

## XML Schema Example

A potential schema structure for the inheritance pattern is as follows:

    <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" elementFormDefault="qualified" targetNamespace="http://www.wetransform.to/ie-examples/inheritance/1.0" version="1.0">
      <import namespace="http://inspire.ec.europa.eu/schemas/base/3.3" schemaLocation="http://inspire.ec.europa.eu/schemas/base/3.3/BaseTypes.xsd"/>
      <xs:element name="CadastralParcel" type="CadastralParcel"/>
      <xs:complexType name="CadastralParcel">
        <xs:sequence>
          <xs:element name="inspireID" type="base:IdentifierPropertyType" />
          <xs:element name="label" type="xs:string" />
          <xs:element name="nationalCadastralReference" type="xs:string" />
        </xs:sequence>
      </xs:complexType>
      <xs:element name="JoinedParcel" type="JoinedParcel"/>
      <xs:complexType name="JoinedParcel">
        <xs:complexContent>
          <xs:extension base="CadastralParcel">
            <xs:sequence>
              <xs:element name="joinedFrom" type="CadastralParcel" minOccurs="1" maxOccurs="unbounded"/>
            </xs:sequence>
          </xs:extension>
        </xs:complexContent>
      </xs:complexType>
    </xs:schema>

The key line that implements the inheritance pattern in XML is ```<xs:extension base="CadastralParcel">```.

Due to the focus on the inheritance pattern, this example uses an in-place encoding of ```CadastralParcel```for the ```joinedFrom``` association. There are other optons for this such as encoding by Reference, which we describe in other patterns.

## XML Instance Example

Instances using this pattern have a simple structure in which the inheritance hierarchy is not apparent:

    <example>
        Some Content
    </example>

## Implementation Considerations

This section provides information when and how this pattern can be implemented on different types of platforms.

### Storage Backend

There is usually no support for inheritance on relational or document-oriented platforms. Some platforms, such as Esri's Geodatabases, support a weaker form of subtyping, where all classes have the same property and differ in just one field value. This form of subtyping is suitable for sets of objects that are similar in data structure, but different in a classification value. An example for subtyping is road, which can have different classes (regional, national, highway).
 
Nonetheless, an inheritance hierarchy can be mapped to a relational or document-oriented structure, and thus maintained. Three common approaches for such mappings include:

1. **Table per class hierarchy**: Use one single table or document collection for all objects of all classes that are part of a given inheritance hierarchy. This can result in sparsely populated tables.
1. **Table per subclass**: Use a separate table for every subclass in the hierarchy, even for abstract classes (classes such as ```AbstractFeatureType``` for which no objects are created, but which are used to built efficient hierarchies). This approach preserves the design structure well, but results in lots of database joins to deliver complete objects.
1. **Table per concrete class**: Use a separate table for each concrete class in the hierarchy, with all fields they inherit from their respective superclasses. This approach doesn't preserve the design structure, but delivers higher performanc,e since no joins are required.

### Download Services

This pattern can be implemented on XML-based platforms without special considerations.

### Business Logic

This pattern can be implemented on object-oriented platforms without special considerations.

### Consumer Side

Most GIS client applications do not make use of inheritance information and just process the instances as they are.


