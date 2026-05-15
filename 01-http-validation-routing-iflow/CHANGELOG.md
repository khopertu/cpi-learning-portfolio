# Changelog

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