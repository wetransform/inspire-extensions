---
layout:                 model
title:                  "Inspire Extensions: INSPIRE for Dutch Provinces"
date:                   2016-05-10 13:20:00 +0200
category:               models
tags:                   [inspire, inspirePS40, inspireAM40, inspireRZ40, inspirePS40, inspireHB40, inspireNRZ40, inspirePIF40, inherit]
modelIdentifier:        dutchprov
modelName:              "INSPIRE for Dutch Provinces"
author:                 "Herman Assink"
modelOrganisation:      "IDGIS"
modelSummary:           "This model makes existing province data available in an INSPIRE-compliant encoding."
modelsReferenced:       [inspirePS40, inspireAM40, inspireRZ40, inspirePS40, inspireHB40, inspireNRZ40, inspirePIF40]
modelReferenceTypes:    [inherit]
modelUsesPatterns:      [inheritance]
modelSchemaLanguage:    "XML Schema"
modelSpokenLanguage:    "Dutch, English"
modelSchemaTool:        ""
modelMaturity:          "Used in production environments"
modelLatestVersion:     ""
modelPreviousVersions:  ""
modelNextVersion:       "At least one more version planned"
modelLicense:           "Open (Unrestricted or attribution-only licenses such as CC-BY, BSD or Apache)"
modelLink:              http://www.idgis.nl
modelStatsSizeTypes:    "-.-"
modelStatsSizeProps:    "-.-"
---

This model was created by IDGIS for the Dutch provinces to satisfy a number of requirements:

* To create a specific data model aligned with INSPIRE Data Specifications, 
* To integrate different data sets
* To create map or download services

A specific issue in the development of this schema was that most GIS clients do not support complex gml.

An example service is available:

* [Area Management WFS GetCapabilities](http://services.inspire-provincies.nl/AreaManagement/services/download_AM?request=GetCapabilities&service=WFS)
