# XML to JSON Transformation iFlow

## Overview

This is a practice SAP Integration Suite / CPI iFlow focused on message transformation.

The goal of this project is to receive XML input, validate it, transform it into JSON, and return the transformed JSON response.

This project pairs with the previous JSON to XML transformation iFlow by implementing the reverse transformation direction.

## Goal

The goal of this iFlow is to practice:

* receiving XML input
* reading values from an XML request body
* validating required fields
* validating allowed field values
* transforming XML into JSON
* returning JSON as the response body
* handling input errors through an Exception Subprocess
* understanding bidirectional message transformation between XML and JSON

## Example Input

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

## Example Output

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "EU"
}
```

## Flow Design

Main flow:

* HTTPS Sender
* GS XML to JSON Transformer
* End

Exception flow:

* Exception Subprocess
* GS Error Handler
* End

The main flow receives an XML request body, validates the required fields and allowed values, transforms the message into JSON, and returns the JSON response.

The exception flow handles client-side input errors such as missing required fields, malformed XML, and invalid allowed values.

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

* XML input parsing
* XML path reading
* JSON response generation
* XML to JSON message transformation
* request body processing
* response body formatting
* input validation
* allowed-value validation
* exception handling

## Why This Matters

In real integration scenarios, different systems often expect different message formats.

One system may send XML, while another system expects JSON. An integration flow can sit between those systems and transform the message so the receiving system can process it correctly.

Together with the JSON to XML project, this project demonstrates bidirectional transformation between XML and JSON formats.

## Current Status

Project 3.0 implemented the initial XML to JSON transformation.

The iFlow receives a nested XML request body, reads values from XML paths using `XmlSlurper`, builds a JSON response using `JsonBuilder`, and returns the transformed JSON response with HTTP `200`.

Project 3.1 added validation and error handling.

Missing required fields, malformed XML body input, and invalid allowed values are handled as client-side input errors and return HTTP `400`.

## Files

* `xml-to-json-transformer.groovy` → parses XML, validates input, and builds the JSON response
* `error-handler.groovy` → handles validation and parsing errors
* `test-cases.md` → documents the tested scenarios
* `CHANGELOG.md` → tracks project versions and improvements
* `screenshots/` → contains test evidence

## Screenshots

* `01-xml-to-json-success-response.png` → valid XML transformed into JSON with HTTP 200
* `02-missing-required-field-error.png` → missing required field handled with HTTP 400
* `03-invalid-source-error-response.png` → invalid `source` handled with HTTP 400
* `04-invalid-region-error-response.png` → invalid `region` handled with HTTP 400
* `05-invalid-xml-body-error-response.png` → malformed XML body handled with HTTP 400
