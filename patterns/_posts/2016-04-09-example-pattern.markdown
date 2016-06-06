---
layout:            pattern
title:             "Example"
author:            "Thorsten Reitz"
date:              2016-04-09 09:10:59 +0200
category:          patterns
tags:              [pattern, model]
patternName:       "Example"
patternType:       model
patternIdentifier: example

---

## Intent

The pattern serves as an example for the structure that we use to describe all patterns.

## When to use

When you need an example and not a real pattern.

## When not to use

When you need a real pattern.

## Structure

This example pattern uses a single, simple type.

## XML Schema Example

The schema structure for this pattern is straightforward:

    <xs:simpleType name="example">
      <xs:restriction base="xs:string">
    </xs:simpleType>

## XML Instance Example

Instances using this pattern have a very simple structure:

    <example>
        Some Content
    </example>

## Implementation Considerations

This section provides information when and how this pattern can be implemented on different types of platforms.

### Storage Backend

This pattern can be implemented on relational platforms without special considerations.

### Download Services

This pattern can be implemented on XML-based platforms without special considerations.

### Business Logic

This pattern can be implemented on object-oriented platforms without special considerations.

### Consumer Side

Typical client applications can consume data using this pattern fully.


