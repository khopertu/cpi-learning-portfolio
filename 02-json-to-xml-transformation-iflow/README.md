# JSON to XML Transformation iFlow

## Overview

This is a practice SAP Integration Suite / CPI iFlow focused on message transformation.

The goal of this project is to receive JSON input, validate it, transform it into XML, and return the transformed XML response.

This project builds on the previous HTTP validation and routing iFlow by moving into a new integration topic: converting data from one message format to another.

## Goal

The goal of this iFlow is to practice:

* receiving JSON input
* reading fields from a JSON request body
* validating required fields
* validating allowed field values
* transforming JSON into XML
* building nested XML structures
* returning XML as the response body
* handling input errors through an Exception Subprocess
* understanding why message transformation is useful in integration scenarios

## Example Input

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "EU"
}
```

## Example Output

```xml
<request>
  <user>
    <name>Alex</name>
  </user>
  <metadata>
    <source>mobile</source>
    <region>EU</region>
  </metadata>
</request>
```

## Flow Design

Main flow:

* HTTPS Sender
* GS JSON to XML Transformer
* End

Exception flow:

* Exception Subprocess
* GS Error Handler
* End

The main flow receives a JSON request body, validates the required fields and allowed values, transforms the message into nested XML, and returns the XML response.

The exception flow handles client-side input errors such as missing required fields, malformed JSON, and invalid allowed values.

## Validation Rules

Required fields:

* `name`
* `source`
* `region`

Allowed `source` values:

* `web`
* `mobile`
* `api`

Allowed `region` values:

* `EU`
* `NA`
* `APAC`

## Key Concepts

This project introduces:

* JSON structure
* XML structure
* root elements
* XML elements/tags
* nested XML structures
* grouping related XML elements
* message transformation
* request body processing
* response body formatting
* input validation
* exception handling

## Why This Matters

In real integration scenarios, different systems often expect different message formats.

One system may send JSON, while another system expects XML. An integration flow can sit between those systems and transform the message so the receiving system can process it correctly.

Nested XML is useful because it groups related data into meaningful sections. In this project, the `name` field is grouped under `user`, while `source` and `region` are grouped under `metadata`.

## Current Status

Project 2.0 introduced the basic JSON to XML transformation.

Project 2.1 added validation and error handling for missing required fields and malformed JSON body input.

Project 2.2 added allowed-value validation for selected fields:

* `source` must be one of: `web`, `mobile`, `api`
* `region` must be one of: `EU`, `NA`, `APAC`

Invalid allowed-value inputs are handled as client-side input errors and return HTTP `400`.

Project 2.3 updated the XML response from a flat structure to a nested structure.

The `name` field is now grouped under a `user` section, while `source` and `region` are grouped under a `metadata` section.

## Files

* `json-to-xml-transformer.groovy` → parses JSON, validates input, and builds the XML response
* `error-handler.groovy` → handles validation and parsing errors
* `test-cases.md` → documents the tested scenarios
* `CHANGELOG.md` → tracks project versions and improvements
* `screenshots/` → contains test evidence

## Screenshots

* `01-post-json-to-xml-success-response.png` → valid JSON transformed into XML with HTTP 200
* `02-missing-required-field-error.png` → missing required field handled with HTTP 400
* `03-invalid-json-body-error.png` → malformed JSON body handled with HTTP 400
* `04-invalid-source-error-response.png` → invalid `source` handled with HTTP 400
* `05-invalid-region-error-response.png` → invalid `region` handled with HTTP 400
* `06-nested-xml-success-response.png` → valid JSON transformed into nested XML with HTTP 200
* `07-invalid-source-after-nested-xml.png` → invalid `source` still handled with HTTP 400 after nested XML update
