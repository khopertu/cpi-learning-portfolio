# CPI Learning Portfolio

This repository contains practical SAP Integration Suite / Cloud Integration learning projects.

## Projects

### [01 - HTTP Validation and Routing iFlow](./01-http-validation-routing-iflow)

A practice iFlow focused on HTTP input validation, default values, Router-based branching, Content Modifier usage, JSON responses, and Exception Subprocess error handling.

This project introduces basic HTTP endpoint handling in SAP Integration Suite / CPI.

---

### Message Transformation Projects

These projects focus on transforming messages between JSON and XML formats.

Together, they demonstrate bidirectional message transformation between systems that use different data formats.

### [02 - JSON to XML Transformation iFlow](./02-json-to-xml-transformation-iflow)

A practice iFlow focused on receiving JSON request body input, validating it, transforming the data into nested XML, and returning an XML response.

This project introduces message transformation concepts including JSON parsing, XML structure, root elements, XML elements/tags, nested XML, allowed-value validation, and response content formatting.

### [03 - XML to JSON Transformation iFlow](./03-xml-to-json-transformation-iflow)

A practice iFlow focused on receiving XML request body input, transforming the data into JSON, and returning a JSON response.

This project pairs with the JSON to XML transformation iFlow by implementing the reverse transformation direction. It introduces XML parsing with `XmlSlurper`, JSON response generation with `JsonBuilder`, and XML path to JSON field mapping.
