# Changelog

## 3.1 - Validation and Error Handling

Added validation and error handling to the XML to JSON transformation iFlow.

### Added

* Required field validation for `name`, `source`, and `region`
* Allowed-value validation for the `source` field
* Allowed-value validation for the `region` field
* Error handling for malformed XML body input
* Exception Subprocess with JSON error responses
* Test cases for missing required fields, invalid values, and malformed XML

### Validation Rules

Allowed `source` values:

* `web`
* `mobile`
* `api`

Allowed `region` values:

* `EU`
* `NA`
* `APAC`

### Verified

* Valid XML returns JSON with HTTP `200`
* Missing required field returns HTTP `400`
* Invalid `source` returns HTTP `400`
* Invalid `region` returns HTTP `400`
* Malformed XML returns HTTP `400`

---

## 3.0 - Basic XML to JSON Transformation

Initial version of the iFlow.

### Added

* HTTPS endpoint for XML input
* Groovy-based XML parsing with `XmlSlurper`
* JSON response generation using `JsonBuilder`
* Basic test case and screenshot evidence for successful XML to JSON transformation

### Verified

* Valid XML returns JSON with HTTP `200`
