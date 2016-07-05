---
layout:            pattern
title:             "INSPIRE Class Extension Pattern: Association"
author:            "Thorsten Reitz"
date:              2016-06-04 10:50:00 +0200
category:          patterns
tags:              [pattern, class]
patternName:       "Association"
patternType:       class
patternIdentifier: association
permalink:         /:categories/:title.html

---

## Intent

An association is a general relation between two or more objects, where at least one of the objects has knowledge of the other one and can access that object's data and methods.  The lifecycle of the associated objects is typically decoupled. The objects can be from different domain models and even managed on different systems - an association is a type of *loose coupling*.

Associations can be unidirectional, bidirectional or multi-directional. In a unidirectional association, the object that has knowledge of the other object is called the *controller*, the other one the *controlled* object. In a bidirectional relationship, both objects know of the other one and can invoke its data and methods. Note that you cannot create an association from an existing INSPIRE class directly, so there can be no bidirectional associations between your class and an INSPIRE class. You will need to [create a subtype](/patterns/inheritance.html) of the INSPIRE class to add that association.

Multi-directional (sometimes called n-ary) associations connect three or more different objects. They are very complex and we don't recommend their usage in INSPIRE extensions.

The objects that are related via the association are considered to act in a role with respect to the association, such as an object of type ```Owner``` acting in the role ```hasOwner``` on a ```Parcel``` object. A role can be used to distinguish two objects of the same class when describing its use in the context of the association, such as a Person who may be in a ```parent``` and a ```child``` role at the same time.

## Structure

When using associations, we connect controllers and controlled objects through a simple association, with arrows indicating visibility:

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/association.png" class="figure-img img-fluid img-rounded" title="Association">
    <figcaption class="figure-caption small"><code>JoinedParcel</code> refers to <code>Owner</code> and to <code>CadastralParcel</code> via an unidirectional association.</figcaption>
</figure>

## When to use

Associations are very versatile, and have several applicable uses:

1. When the associated objects have different lifespans
1. When associated objects should be re-used many times, e.g. in N:M relationships
1. Generally, when loose coupling of objects is desirable, e.g. when the associated objects are part of different domains or implemented in different systems
1. When coupling objects of two classes, but not more (for that you should instead create new types)

## When not to use

Associations should not be used when:

1. Strong coupling is desirable (whole-part relationships, shared lifecycle)

## XML Schema Example

A potential schema structure for the association pattern is as follows:

<pre data-line="11,28,29" class="line-numbers" data-src="/patterns/examples/association.xsd">
<code class="language-xml">
</code>
</pre>

[Download the Example Schema](/patterns/examples/association.xsd)

In line 11, we set the ```Owner``` element's ```substitutionGroup``` to ```AbstractFeature```. This is mandatory to allow objects of this type to be included in the container, a ```wfs:FeatureCollection```. In lines 28 and 29, we use ```gml:ReferenceType``` as the type of the properties, instead of the actual types the references point to (which could be ```CadastralParcel``` and ```Owner```). This means we lose some information in the XML schema. There are several ways to encode this information, such as using annotations or optional attributes of the `xlink`, but none of them were standardised in INSPIRE.

<table class="alert-warning important-info">
    <tr>
        <td style="width:3em"><div class="important-info-icon"><span class="glyphicon glyphicon-exclamation-sign" style="font-size:2em"></span></div></td>
        <td>Please note that we create a new <code>Owner</code> class instead of re-using one of the metadata classes such as <code>CI_ResponsibleParty</code>, since the latter cannot exist as a stand-alone member of a <code>FeatureCollection</code>.</td>
    </tr>
</table>

## XML Instance Example

Instances using this pattern are usually encoded as follows:

<pre class="line-numbers" data-src="/patterns/examples/association.xml">
<code class="language-xml">
</code>
</pre>

[Download the Example Instance](/patterns/examples/association.xml)

We use ```gml:ReferenceTypes``` to encode the association. Such references use XLinks to point to any local or remote resource with a resolvalbe URL. In this case, we define in-document anchors such as ```#owner_1``` to point to any element identified by an ```id```.

## Implementation Considerations

This section provides information when and how this pattern can be implemented on different types of platforms.

### Storage Backend

Association is well-supported by all types of storage backends. Some backends, such as document-oriented databases, have limitations in the way how they enforce referential integrity when such patterns are used.

### Download Services

This pattern can be implemented on XML-based platforms without special considerations.

### Business Logic

This pattern can be implemented on object-oriented platforms without special considerations.

### Consumer Side

Most GIS clients do no have special handling for associations. As an example, associations encoded using ```xlink:href``` are usually normal attributes for GIS clients, and not automatically expanded. Similar to Aggregation, ArcGIS can emulate associations via the definition of client-side *Relates*.


