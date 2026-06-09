# Changelog

## 2.2 - Allowed-Value Validation

Added allowed-value validation for selected input fields.

### Added

* Allowed-value validation for the `source` field
* Allowed-value validation for the `region` field
* Error handling for invalid allowed-value inputs
* Test cases for invalid `source` and invalid `region`

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

* Invalid `source` returns HTTP `400`
* Invalid `region` returns HTTP `400`
* Valid JSON input still returns XML with HTTP `200`

## 2.1 - Validation and Error Handling

Added validation and error handling to the JSON to XML transformation iFlow.

### Added

- Required field validation for `name`, `source`, and `region`
- Error handling for malformed JSON body input
- Exception Subprocess with JSON error responses
- Test cases for missing required fields and malformed JSON

### Changed

- Updated the JSON to XML transformer to validate input before building XML
- Renamed internal values to `nameValue`, `sourceValue`, and `regionValue` to avoid conflicts with XML element names in `MarkupBuilder`

### Verified

- Valid JSON returns XML with HTTP `200`
- Missing required field returns HTTP `400`
- Malformed JSON returns HTTP `400`

---

## 2.0 - Basic JSON to XML Transformation

Initial version of the iFlow.

### Added

- HTTPS endpoint for JSON input
- Groovy-based JSON parsing
- XML response generation using `MarkupBuilder`
- Basic test case and screenshot evidence for successful JSON to XML transformation