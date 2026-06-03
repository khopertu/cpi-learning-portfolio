# Changelog

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