# CPI Learning Portfolio

This repository contains practical SAP Integration Suite / Cloud Integration learning projects.

The goal of this portfolio is to demonstrate hands-on understanding of integration flows, HTTP endpoints, message transformation, validation, routing, error handling, and basic integration design patterns.

## Portfolio Focus

This portfolio currently focuses on:

* SAP Integration Suite / Cloud Integration
* HTTP-based iFlows
* JSON and XML message handling
* Groovy scripting
* Message validation
* Exception Subprocess error handling
* Bidirectional JSON/XML transformation
* Test documentation and screenshot evidence

Each project includes implementation notes, test cases, Groovy scripts, screenshots, and changelog-style versioning where relevant.

---

## Projects

### [01 - HTTP Validation and Routing iFlow](./01-http-validation-routing-iflow)

A practice iFlow focused on HTTP request handling, input validation, default values, Router-based branching, Content Modifier usage, external receiver calls, JSON responses, and Exception Subprocess error handling.

This project demonstrates how an iFlow can receive HTTP input, normalize and validate request data, route messages based on derived properties, call a receiver system, and return structured success or error responses.

Key concepts:

* HTTPS Sender
* GET query parameters
* POST JSON body input
* Router
* Content Modifier
* Request Reply
* Groovy parsing and response building
* Client-side error handling with HTTP `400`
* Backend/technical error handling with HTTP `502`

---

## Message Transformation Projects

Projects 2 and 3 are paired transformation projects.

Together, they demonstrate bidirectional message transformation between JSON and XML formats.

### [02 - JSON to XML Transformation iFlow](./02-json-to-xml-transformation-iflow)

A practice iFlow focused on receiving JSON request body input, validating it, transforming the data into nested XML, and returning an XML response.

This project introduces message transformation concepts including JSON parsing, XML structure, root elements, XML elements/tags, nested XML, allowed-value validation, and response content formatting.

Transformation direction:

```text
JSON input → XML output
```

Current output structure:

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

Key concepts:

* `JsonSlurper` for JSON parsing
* `MarkupBuilder` for XML generation
* Required field validation
* Allowed-value validation
* Malformed JSON handling
* Nested XML output
* Exception Subprocess error handling

---

### [03 - XML to JSON Transformation iFlow](./03-xml-to-json-transformation-iflow)

A practice iFlow focused on receiving XML request body input, validating it, transforming the data into nested JSON, and returning a JSON response.

This project pairs with the JSON to XML transformation iFlow by implementing the reverse transformation direction. It introduces XML parsing with `XmlSlurper`, JSON response generation with `JsonBuilder`, and XML path to JSON field mapping.

Transformation direction:

```text
XML input → JSON output
```

Current output structure:

```json
{
  "user": {
    "name": "Alex"
  },
  "metadata": {
    "source": "mobile",
    "region": "EU"
  }
}
```

Key concepts:

* `XmlSlurper` for XML parsing
* `JsonBuilder` for JSON generation
* XML path reading
* Required field validation
* Allowed-value validation
* Malformed XML handling
* Nested JSON output
* Exception Subprocess error handling

---

## Shared Validation Rules

The transformation projects use the same business validation rules.

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

Client-side input errors return HTTP `400`.

Unexpected technical or backend errors return HTTP `502`.

---

## What This Portfolio Demonstrates

This portfolio demonstrates practical experience with:

* creating SAP Integration Suite packages and iFlows
* configuring HTTPS sender endpoints
* deploying and testing CPI iFlows
* handling JSON and XML request bodies
* transforming messages between JSON and XML
* using Groovy scripts in integration flows
* validating required fields and allowed values
* returning structured success and error responses
* using Exception Subprocesses for error handling
* documenting test cases and implementation changes
* maintaining a GitHub-based learning portfolio

---

## Current Status

Completed projects:

* Project 1 - HTTP Validation and Routing
* Project 2 - JSON to XML Transformation
* Project 3 - XML to JSON Transformation

Next planned project:

* Project 4 - External Receiver Call and Response Handling

The next project will focus on calling an external receiver system, processing the receiver response, and handling backend/system errors in a more realistic integration scenario.
