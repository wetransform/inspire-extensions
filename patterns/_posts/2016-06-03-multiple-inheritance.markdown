---
layout:            pattern
title:             "INSPIRE Class Extension Pattern: Multiple Inheritance"
author:            "Thorsten Reitz"
date:              2016-06-01 09:30:00 +0200
category:          patterns
tags:              [pattern, class]
patternName:       "Multiple Inheritance"
patternType:       class
patternIdentifier: mixin
permalink:         /:categories/:title.html

---

## Intent

In multiple inheritance, the class we design is composed of properties of multiple other classes. In multiple inheritance, there is no clear inheritance hierarchy anymore, which may lead to conflicting concepts. Therefore multiple inheritance is not recommended, however there are several mechanisms for multiple inheritance, one of which is called *Mixin*, that are useful. Each mixin is a collection of methods or properties that can be injected into the new class. Mixins are typically narrow in scope and not meant to be extended by themselves. 

In INSPIRE, we can use mixins to reuse both INSPIRE specifications and local specifications, and to make sure our new class is syntactically compatible to both.

## Structure

In this pattern, there is a child class, at least one mixin class and an optional parent class. The child class and the mixin class are connected through a Generalisation association which points from the child to the mixin. In this example, we add information on the position and level of access to our classes  by mixing in a class called `AccessPoint`:

<figure class="figure" style="margin-bottom: 20px">
    <img src="/patterns/images/mixin.png" class="figure-img img-fluid img-rounded" title="Mixin">
    <figcaption class="figure-caption small"><code>JoinedParcel</code> inherits all properties from <code>CadastralParcel</code> and mixes in <code>AccessPoint</code>. Note that the mixin class association has a tag set to indicate the mixin approach.</figcaption>
</figure>

## When to use

In the context of an INSPIRE Data Specification Extension, there are two cases where multiple inheritance via mixin can be effective:

1. Your class should be compatible with an INSPIRE class <strong>and</strong> local standard classes.
1. You want to use a common set of properties with consistent property names to new multiple subclasses of INSPIRE classes.

## When not to use

Support for multiple inheritance via mixin varies greatly across different implementation platforms and can be complex to realize, which is the main downside. There are also other indicators when not to use mixins:

1. When one of the other patterns also satisfies your requirements.
1. You want to use multiple classes from the same hierarchy, as mixing them in will result in duplication or overloading.
1. You need to bring in a single property or a set of unrelated properties from the mixin(s)
1. You want to have the mixin conceptual relationship to be present in the implementation as well

## XML Schema Example

There is no direct support for multiple inheritance or mixins in XML Schema. A common approach is to do a schema generation that copies the properties and methods of the mixin to the new class. Shapechange is able to do this when you add a UML tag to the mixin class: `gmlMixin = true`. The result looks as in this example:

<pre data-line="20,21" class="line-numbers" data-src="/patterns/examples/mixin.xsd">
<code class="language-xml">
</code>
</pre>

In the example, we make our new class ```JoinedParcel``` accessible by copying the two properties `accessGeometry` and `accessLevel` from the class `AccessPoint`. Note that that the `AccessPoint` is abstract; it's not intended to be used directly to create objects using it.

[Download the Example Schema](/patterns/examples/mixin.xsd)

## XML Instance Example

Instances using this pattern have a simple structure in which the properties inherited through mix-in can usually be identified through their namespace prefix:

<pre class="line-numbers" data-src="/patterns/examples/mixin.xml">
<code class="language-xml">
</code>
</pre>

[Download the Example Instance](/patterns/examples/mixin.xml)

## Implementation Considerations

This section provides information when and how this pattern can be implemented on different types of platforms.

<table class="alert-warning important-info">
    <tr>
        <td style="width:3em"><div class="important-info-icon"><span class="glyphicon glyphicon-exclamation-sign" style="font-size:2em"></span></div></td>
        <td>Mixin is the least commonly used class extension pattern and is not supported directly in most implementation platforms.</td>
    </tr>
</table>

### Storage Backend

There is no storage backend type available that supports mixins directly. In any kind of relational or document-oriented storage, the information about the mixin structure would be lost. Usage of the pattern has no averse effect on storage size.

### Download Services

As there is no direct support of mixins or multiple inheritance in XML Schema, a Download Service implementation will not reflect the mixin conceptual schema directly. You will have to define which pattern of schema transformation you want to use:

1. Use named model groups and named property groups
1. Copy properties from the mixin class to the target class

### Business Logic

Some programming languages either provide support for mixins directly or through extensions.

### Consumer Side

No GIS client software is able to directly work with Mixins. As for the storage backend and download services, clients will interpret the instance documents with normal properties, and not be able to usnerstand that there was a mixin at the level of the conceptual schema.


