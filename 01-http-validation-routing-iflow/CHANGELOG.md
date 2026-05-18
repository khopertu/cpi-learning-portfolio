# Changelog

## 1.2 - Generic Field Validation Messages

Updated validation error wording to support both query-parameter input and JSON body input more cleanly.

### Changed

- Replaced query-param-specific validation wording with generic field-based wording
- Changed `Missing query param 'name'` to `Missing required field 'name'`
- Changed `Invalid query param 'source'` to `Invalid value for field 'source'`
- Changed `Invalid query param 'region'` to `Invalid value for field 'region'`
- Updated Error Handler matching logic for the new validation messages
- Updated test case expectations for the new error messages

### Verified

- Missing `name` still returns HTTP `400`
- Invalid `source` still returns HTTP `400`
- Existing successful query-param and JSON body flows remain unchanged

---

## 1.1 - JSON Body Input Support

Added support for POST requests with JSON body input.

### Added

- JSON body parsing using `JsonSlurper`
- `Content-Type: application/json` handling
- POST-style request testing
- malformed JSON handling with HTTP `400`
- screenshot evidence for successful POST JSON input

### Changed

- Parse script now supports two input styles:
  - query parameters
  - JSON request body

### Preserved

The downstream flow design remains mostly unchanged:

- Router still branches on `channel`
- branch Content Modifiers still set `flowType` and `priority`
- Response Builder still returns structured JSON
- Exception Subprocess still handles error responses

---

## 1.0 - Initial Query Parameter Version

Initial version of the iFlow.

### Added

- HTTPS Sender endpoint
- query parameter parsing
- required `name` validation
- default values for optional fields
- allowed-value validation for `source` and `region`
- Router-based branching using `channel`
- branch-specific Content Modifier properties
- JSON response builder
- Exception Subprocess error handling
- test cases and screenshot evidence