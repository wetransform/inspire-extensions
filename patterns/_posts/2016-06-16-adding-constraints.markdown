---
layout:            pattern
title:             "INSPIRE Class Extension Pattern: Adding Constraints"
author:            "Thorsten Reitz"
date:              2016-06-16 09:40:00 +0200
category:          patterns
tags:              [pattern, property]
patternName:       "Adding Constraints"
patternType:       property
patternIdentifier: constraint
permalink:         none

---

## Intent

Constraints are restrictions that allow us to exactly describe what a valid data structure looks like. In a very simple form, a constraint limits the values a certain property can take, similar to assigning a code list to the property. Constraints can be used to define a range of valid values, or to define requirements on the geometry, such as that the outer ring of polygon must have a counter-clockwise winding order. Constraints can also be used to enforce consistency across multiple properties or even objects of multiple classes. An example for this is ensuring that a centroid geometry of an object lies inside the surface geometry property that the same object also has.

By defining additional constraints on an INSPIRE class, we can use some of the more open structures and scope them down, so that they better match our own requirements. An example for this is the `geometry` property on any `FeatureType`. Often, any subtype of `AbstractGeometry` is allowed, such as `Point`, `MultiSurface` or even `RectifiedGrid`. This flexibility doesn't help implementation of clients and business logic that need to consume this data, so it makes sense to explicitly define what kind of data we are actually able to process.

We also define constraints to remove ambiguity between the text definition of the data model (the Data Specification) and the logical schema (the GML application schema). We use GML application schemas, being XML schema, to express several basic constraints that operate on a single element and its content, such as the following:

* `minOccurs`, `maxOccurs`: How often can an element occur?
* `nillable`: Is it allowed for the element to have empty content?
* `type`: What types are allowed as value inside the element?
* `minInclusive`, `maxInclusive`: What range of integer values is allowed?
* `pattern`: Does the content of the element match the regular expression given in pattern?
* `length`, `minLength`, `maxLength`: Does the length of the string content match the allowed length?

To add XML schema constraints, we need to create a new subclass of the INSPIRE class and then add the additional constraints to the new subtype. An alternative is to use a Schematron constraint, which we define outside of the GML application schema. In this case, we don't need to create a new GML application schema.

If we need to go beyond that, in particular to express cross-element constraints, we use specific constraint languages such as [Schematron](https://en.wikipedia.org/wiki/Schematron), or XSLT processors with additional custom functions. 

## Structure

UML has a specific language for expressing constraints called the Object Constraint Language (OCL).

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/constraints.png" class="figure-img img-fluid img-rounded" title="Code List Extension">
    <figcaption class="figure-caption small">We require that the <code>geometry</code> of <code>Building</code> is entirely contained by the geometry of the <code>Parcel</code>  referenced through the association <code>onParcel</code>.</figcaption>
</figure>

In this example, we add an association called `onParcel` to `Building`, to link the `Building` to a `Parcel` it is built on. We require that the `geometry` of the `Building` has to be fully contained by the `geometry` of the associated `Parcel`.

<table class="alert-warning important-info">
    <tr>
        <td style="width:3em"><div class="important-info-icon"><span class="glyphicon glyphicon-exclamation-sign" style="font-size:2em"></span></div></td>
        <td>None of the common constraint languages has support for spatial operations such as the OCL <code>isContainedBy</code> operation referenced above. These have to be implemented separately.</td>
    </tr>
</table>

## When to use

Similar to code list extension, adding constraints is relatively easy to implement, but covers only a few specific cases:

1. When the values you want to use are always a well-defined subset of what is allowed by the schema
1. When you need to ensure consistent values across multiple properties, types or even data sets to satisfy modelling requirements

## When not to use

Constraint extension is limited in scope. Constraints may not be used to profile an INSPIRE schema in such a way that a data set that is compliant with the constraint is not INSPIRE-compliant anymore, so you should'nt use constraints in this case:

1. When the constraints you add make data in the constrained data model incompatible to INSPIRE.
 
## XML Schema Example

If you add the new constraints via an extra file, there is no need to modify or create an existing GML Application Schema. If you want to change or add the constraints defined in the INSPIRE schema, you have to be very careful to not break the rules of INSPIRE extensions in general, as most changes will reduce interoperability.

What you need to add is the implementation of the OCL constraint you've modelled before. On the XML implementation platform, we can use Schematron. Schematron, by itself, can't do spatial operations. 

<pre class="line-numbers" data-src="/patterns/examples/schematron-rule.xml">
<code class="language-xml">
</code>
</pre>

[Download the Schematron rule example](/patterns/examples/schematron-rule.xml)

Here, we search for `ExtendedBuilding` instances by using these as a context for the rule, extract the local ID value from the `onParcel` reference, and then verify whether there is a `Parcel` that has the referenced `localId` in its `inspireId` property.

## XML Instance Example

A GML instance doesn't need to use any specific elements to support being validated through a constraint framework. Here is an instance that fulfills the schematron rule we defined previously:

<pre class="line-numbers" data-src="/patterns/examples/schematron-instance.xml">
<code class="language-xml">
</code>
</pre>

[Download this GML Example](/patterns/examples/schematron-instance.xml)<br/>
[Download the matching GML Application Schema](/patterns/examples/constraints.xsd)

## Implementation Considerations

This section provides information when and how this pattern can be implemented on different types of platforms.

### Storage Backend

Most platforms for data storage provide rich constraint definition options. Constraints are a key part of SQL, with options such as Foreign Key Relationships, uniqueness constraints and many more. Backends based on JSON usually offer lower expressiveness in terms of constraints and validation.

### Download Services

This pattern can be implemented on XML-based platforms without special considerations.

### Business Logic

This pattern can be implemented on object-oriented platforms without special considerations.

### Consumer Side

While clients often don't have direct support for validation against additional constraints, the constraints usually simplify data usage on the client, since we can define the constraints so that they match our client's capabilities better.


