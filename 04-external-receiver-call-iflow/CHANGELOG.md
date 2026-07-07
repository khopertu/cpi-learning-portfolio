# Changelog

## 4.0 - External Receiver Call and Standard Mapping

Initial and complete version of the iFlow.

### Added

* HTTPS endpoint accepting a dynamic `id` query parameter
* Groovy-based `id` validation (required, positive integer)
* Dynamic receiver addressing via `CamelHttpUri` header override, built from the validated `id`
* Request Reply call to the JSONPlaceholder external API
* Standard JSON-to-XML Converter step
* Message Mapping reshaping the receiver response into a clean, nested structure (dropping unused fields, renaming and nesting the rest)
* Standard XML-to-JSON Converter step, with "Suppress JSON Root Element" enabled
* Exception Subprocess distinguishing three error cases: input validation (`400`), receiver not-found (`404` passthrough), and receiver connectivity/technical failure (`502`)

### Fixed During Testing

* `CamelHttpQuery` was not cleared after validation, causing the original incoming query string to leak onto the outgoing receiver URL. Fixed by explicitly clearing the header in `id-validator.groovy`.
* The Message Mapping's root element (`response`) was leaking into the final JSON as a wrapper key. Fixed by enabling "Suppress JSON Root Element" on the XML-to-JSON Converter.

### Verified

* Missing `id` returns HTTP `400`
* Non-numeric `id` returns HTTP `400`
* `id=0` returns HTTP `400`
* Valid `id` returns HTTP `200` with correctly reshaped, nested JSON and no dropped fields present
* Receiver `404` (user not found) is passed through as HTTP `404`, not converted to `502`
* Receiver unreachable (broken hostname) returns HTTP `502`
