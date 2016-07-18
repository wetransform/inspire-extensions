---
layout:            pattern
title:             "INSPIRE Class Extension Pattern: Aggregation"
author:            "Thorsten Reitz"
date:              2016-07-03 09:50:00 +0200
category:          patterns
tags:              [pattern, class]
patternName:       "Aggregation"
patternType:       class
patternIdentifier: aggregation
permalink:         /:categories/:title.html

---

## Intent

In aggregation, a whole data structure is made of parts belonging to one or more classes, such as a student belonging to multiple classes in their course of studies. Similar to composition, aggregation is a form of object combination. In software engineering, we often make a distinction between composition and aggregation:

1. In aggregation, the lifecycle of the contained object is independent of the lifecycle of the containing object. The contained object ("containee") can thus exist independently and be shared by more than one containing object.
2. 1. In [composition](/patterns/composition.html), the lifecycle of the contained object is bound to the lifecycle of the containing object. There is exactly one object that owns the contained objects.

In aggregation, the container is incomplete when it's missing the containee. A car without wheels isn't complete, but the wheel can be mounted on a different car. Note that the encoding for the [Association](/patterns/2016/06/03/association.html) pattern is the same as for aggregation. 

## Structure

In aggregation, there is a containing class and a contained class. They are connected through an aggregation association which points from the containing class to the contained class:

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/aggregation.png" class="figure-img img-fluid img-rounded" title="Aggregation">
    <figcaption class="figure-caption small"><code>JoinedParcel</code> aggregates an <code>owner</code>.</figcaption>
</figure>

<table class="alert-warning important-info">
    <tr>
        <td style="width:3em"><div class="important-info-icon"><span class="glyphicon glyphicon-exclamation-sign" style="font-size:2em"></span></div></td>
        <td>Please note that we create a new <code>Owner</code> class instead of re-using one of the metadata classes such as <code>CI_ResponsibleParty</code>, since the latter cannot exist as a stand-alone member of a <code>FeatureCollection</code>.</td>
    </tr>
</table>

## When to use

Aggregation is a very versatile, well-supported pattern, and has several applicable uses:

1. When there is a whole-part relationship between the objects
1. When the lifecycle of the containees is linked to more than one container
1. When inheritance is not applicable because the ```is-a``` statement is not true and would break proper encapsulation.

## When not to use

Aggregation implies a contains/contained relationship, and provides looser coupling than composition in terms of ownership and lifespans. It is thus not suitable for coupling of all types of closely related objects:

1. You want to relate to an object where there is no aggregation (e.g. neighboringHouse)
1. You want to combine objects that have a fully dependant lifecycle

## XML Schema Example

A potential schema structure for the aggregation pattern is as follows:

<pre data-line="23" class="line-numbers" data-src="/patterns/examples/aggregation.xsd">
<code class="language-xml">
</code>
</pre>

[Download the Example Schema](/patterns/examples/aggregation.xsd)

In line 23, we define the contained property like any other property in the sequence. We declare it to be of type ```ex:OwnerType```. This schema allows us to do different types of encodings for the instance, as described below.

## XML Instance Example

Instances using this pattern are usually encoded using a by-reference encoding to enable many-to-many relationships:

<pre class="line-numbers" data-src="/patterns/examples/aggregation.xml">
<code class="language-xml">
</code>
</pre>

[Download the Example Instance](/patterns/examples/aggregation.xml)


## Implementation Considerations

This section provides information when and how this pattern can be implemented on different types of platforms.

### Storage Backend

Composition is well-supported by all types of storage backends. In-line encoding in document-oriented backends has the advantage of not requiring joins.

### Download Services

This pattern can be implemented on XML-based platforms without special considerations.

### Business Logic

This pattern can be implemented on object-oriented platforms without special considerations.

### Consumer Side

Many GIS applications support nested composition structures to some degree. As an example, ArcGIS geodatabases support *Relationship classes* and client-sied *Relates*, which you can use to define both aggregation and composition associations between different classes. However, it's not possible to use nested properties for styling or in many analytic tools. For this purpose, you have to use client-side *Joins*.


