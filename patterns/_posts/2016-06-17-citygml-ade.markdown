---
layout:            pattern
title:             "INSPIRE Model Extension Pattern: CityGML Application Domain Extension"
author:            "Thorsten Reitz"
date:              2016-06-17 09:40:00 +0200
category:          patterns
tags:              [pattern, model]
patternName:       "CityGML Application Domain Extension"
patternType:       model
patternIdentifier: ade
permalink:         none

---

## Intent

We usually encode INSPIRE data using a OGC GML-based encoding. There are also other standards frameworks that have been built on OGC GML, such as CityGML 2.0. CityGML 2.0 is a standard with modules such as `Core`, `Building` and `Bridge`. The standard also contains provisions on how to extend the core schemas. These provisions are called Application Domain Extensions, and more than 20 of these have been published to date. Compared to the INSPIRE Generic Conceptual Model provisions on extensions, ADEs provide a few additional guidelines, e.g. on scope. An ADE is furthermore less strict when it comes to maintaining compatibility to the extended schemas - it allows attribute type substitution to incompatible types.

CityGML and INSPIRE also have some overlap, since the INSPIRE Buildings3D schema has strongly been influenced by CityGML and uses many of the same concepts, such as the Level of Detail approach.

## General Rules

Application Domain Extensions (ADE) specify additions to the core CityGML data model. Each ADE should be specific - it should cover one well-defined use case. Such additions introduce new properties to existing CityGML classes like the number of inhabitants of a building or the definition of new object types. We define an ADE in an separate XML schema definition file with its own namespace. This file has to explicitly import the XML Schema definition of the extended CityGML modules.

ADEs may import one or more CityGML modules as well as other schemas, such as INSPIRE schemas, so that we can limit the conceptual overhead that would result from importing the entire model. The ADE mechanism is orthogonal to the modularisation approach of CityGML. Extended CityGML instance documents validate against the CityGML schema and the respective ADE schema. You can use more than one ADE can in the same data set. 

Other rules:

* Nested ADEs are not permitted;
* You can also add attributes through the `GenericAttributes` mechanism, which means you don't have to build a derived schema;
* You can either constrain property types to more specific subtypes, or substitute them with incompatible types.

<table class="alert-warning important-info">
    <tr>
        <td style="width:3em"><div class="important-info-icon"><span class="glyphicon glyphicon-exclamation-sign" style="font-size:2em"></span></div></td>
        <td>Using an incompatible property type usually breaks INSPIRE compatibility.</td>
    </tr>
</table>

## Technical Approach

CityGML 2.0 is an Application Schema based on GML 3.1.1, while INSPIRE is based on GML 3.3. CityGML 3.0 will also be based on GML 3.3. CityGML 2.0 doesn't contain provisions on the conceptual modelling of an ADE in UML, though there is a best practice papers on how to do this. Furthermore, the working group intends to include such guidelines with the 3.0 version of the standard.
 
Some draft guidelines have already emerged:
 
* Usage of stereotypes
    * `<<ADE>>` Indicate that ADE rules regarding inheritance need to be applied in schema generation on the type
    * `<<ADEElement>>` Indicate that the particular element (tye/property/association) has special tagged values that describe substitution and other special rules
