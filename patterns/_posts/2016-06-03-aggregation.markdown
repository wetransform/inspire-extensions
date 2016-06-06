---
layout:            pattern
title:             "INSPIRE Class Extension Pattern: Aggregation"
author:            "Thorsten Reitz"
date:              2016-06-03 09:50:00 +0200
category:          patterns
tags:              [pattern, class]
patternName:       "Aggregation"
patternType:       class
patternIdentifier: aggregation

---

## Intent

Aggregation is a form of object composition, where a whole data structure is made of parts belonging to one or more classes, such as a car also having an engine and four wheels. Aggregation is usually implemented so that an object contains another object. In software engineering, we often make a distinction between composition and aggregation:

1. In composition, the lifecycle of the contained object is bound to the lifecycle of the containing object.
1. In aggregation, the lifecycle of the contained object is independent of the lifecycle of the containing object. The contained object can thus exist independently and even be shared by more than one containing object.

For the purpose of INSPIRE extensions, we'll refer to both modes as aggregation. The defining characteristic of the aggregation is that we build a whole object from parts, where there is a ```contains``` association between the parts.

## Structure

In aggregation, there is a containing class and a contained class. They are connected through an Aggregation association which points from the containing class to the contained class:

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/aggregation.png" class="figure-img img-fluid img-rounded" title="Aggregation">
    <figcaption class="figure-caption small"><code>JoinedParcel</code> inherits all properties from <code>CadastralParcel</code> and adds a <code>joinedFrom</code> property.</figcaption>
</figure>

## When to use

Aggregation is a very versatile, well-supported pattern, and has several applicable uses:

1. When there is a clear whole-part relationship between the objects
1. When the lifecycle of the aggregated objects is linked
1. When inheritance is not applicable because the ```is-a``` statement is not true and would break proper encapsulation.

## When not to use

Aggregation implies a contains/contained relationship, and often also mandates linked lifespans. It is thus not suitable for coupling of loosely related objects:

1. You want to point at a related object where there is no composition (e.g. neighboringHouse)
1. You want to link objects that have a different lifecycle

## XML Schema Example

A potential schema structure for the aggregation pattern is as follows:

<pre data-line="23" class="line-numbers" data-src="/patterns/examples/aggregation.xsd">
<code class="language-xml">
</code>
</pre>

[Download the Example Schema](/patterns/examples/aggregation.xsd)

In line 23, we define the aggregated property like any other property in the sequence. We declare it to be of type ```ex:OwnerType```. This schema allows us to do different types of encodings for the instance, as described below.

## XML Instance Example

Instances using this pattern can be encoded with different strategies. In particular when you have shared contained objects, we recommend to use encoding by reference:

<pre class="line-numbers" data-src="/patterns/examples/aggregation.xml">
<code class="language-xml">
</code>
</pre>

[Download the Example Instance](/patterns/examples/aggregation.xml)

This encoding strategy allows us to re-use contained objects, instead of having to repeat them in each containing object. In this way, a single ```ex:Owner``` object can be shared by many ```ex:JoinedParcel``` objects.

## Implementation Considerations

This section provides information when and how this pattern can be implemented on different types of platforms.

### Storage Backend

Aggregation is well-supported by all types of storage backends. Some backends, such as document-oriented databases, have limitations in the way in which encoding by reference can be used.

### Download Services

This pattern can be implemented on XML-based platforms without special considerations.

### Business Logic

This pattern can be implemented on object-oriented platforms without special considerations.

### Consumer Side

Many GIS applications support nested aggregation structures to some degree. As an example, ArcGIS geodatabases support Relationship classes, which you can use to define both aggregation and composition associations between different classes. However, it's not possible to use nested properties for styling or in many analytic tools.


