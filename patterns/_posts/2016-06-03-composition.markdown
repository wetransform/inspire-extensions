---
layout:            pattern
title:             "INSPIRE Class Extension Pattern: Composition"
author:            "Thorsten Reitz"
date:              2016-06-03 09:50:00 +0200
category:          patterns
tags:              [pattern, class]
patternName:       "Composition"
patternType:       class
patternIdentifier: composition

---

## Intent

Composition is a strong form of object combination, where a whole data structure is made of parts belonging to one or more classes, such as a car also having an engine and four wheels. In software engineering, we often make a distinction between composition and aggregation:

1. In composition, the lifecycle of the contained object is bound to the lifecycle of the containing object. There is exactly one object that owns the contained objects.
1. In aggregation, the lifecycle of the contained object is independent of the lifecycle of the containing object. The contained object ("containee") can thus exist independently and be shared by more than one containing object.

This pattern describes composition only. In composition, you extension object always **contains** an INSPIRE object. If the lifecycle of your extension object ends, the INSPIRE object would have to be removed as well. The other way around is not possible since you can't modify the INSPIRE class to add the new relation.

If you discover need for a composition, make sure you resolve the associated organisational issues that come from ownership of the composed class. You should only use composition between data sets maintained by different organisations if you also have a trusted working relationship with these organisations.

## Structure

In composition, there is a containing class and a contained class. They are connected through an Composition association which points from the containing class to the contained class:

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/composition.png" class="figure-img img-fluid img-rounded" title="Composition">
    <figcaption class="figure-caption small"><code>JoinedParcel</code> contains an <code>owner</code> property.</figcaption>
</figure>

## When to use

Composition is a very versatile, well-supported pattern, and has several applicable uses:

1. When there is a clear whole-part relationship between the objects
1. When the lifecycle of the composed objects is linked
1. When inheritance is not applicable because the ```is-a``` statement is not true and would break proper encapsulation.

## When not to use

Composition implies a contains/contained relationship, and often also mandates linked lifespans. It is thus not suitable for coupling of loosely related objects:

1. You want to point at a related object where there is no composition (e.g. neighboringHouse)
1. You want to link objects that have a different lifecycle
1. You need a many-to-many relationship between containers and containees
1. You need shared ownership, where a containee can be part of multiple containers (e.g. a `Student` is in multiple `Course`s)

## XML Schema Example

A potential schema structure for the composition pattern is as follows:

<pre data-line="23" class="line-numbers" data-src="/patterns/examples/composition.xsd">
<code class="language-xml">
</code>
</pre>

[Download the Example Schema](/patterns/examples/composition.xsd)

In line 23, we define the contained property like any other property in the sequence. We declare it to be of type ```ex:OwnerType```. This schema allows us to do different types of encodings for the instance, as described below.

## XML Instance Example

Instances using this pattern are usually encoded using an in-place encoding:

<pre class="line-numbers" data-src="/patterns/examples/composition.xml">
<code class="language-xml">
</code>
</pre>

[Download the Example Instance](/patterns/examples/composition.xml)

This encoding strategy means we can't re-use contained objects. Please refer to the Association pattern to learn how a single ```ex:Owner``` object can be shared by many ```ex:JoinedParcel``` objects.

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


