# External Receiver Call and Standard Mapping iFlow

## Overview

This is a practice SAP Integration Suite / CPI iFlow focused on calling an external receiver system and reshaping its response using standard CPI tools instead of Groovy.

The goal of this project is to receive a dynamic HTTP request, call an external API, and return a cleaner, reshaped response — while correctly separating client input errors, not-found responses, and backend/technical failures.

This project moves the portfolio from Groovy-heavy transformation (Projects 2 and 3) toward platform-native CPI tooling: standard converters and Message Mapping.

## Goal

The goal of this iFlow is to practice:

* receiving a dynamic HTTP request via a query parameter
* validating required and allowed input
* building a receiver call path dynamically from that input
* calling an external API through Request Reply
* converting a JSON receiver response into XML
* reshaping the response using Message Mapping
* converting the mapped XML back into JSON
* returning a clean, structured response
* distinguishing client errors (`400`), not-found responses (`404`), and backend/technical failures (`502`)
* keeping Groovy minimal, used only where standard steps cannot handle the logic

## Example Input

```http
GET /user-lookup?id=1
```

## Example Output

```json
{
  "customer": {
    "id": 1,
    "name": "Leanne Graham",
    "contactEmail": "Sincere@april.biz"
  },
  "location": {
    "city": "Gwenborough"
  },
  "organization": {
    "name": "Romaguera-Crona"
  }
}
```

## Flow Design

Main flow:

* HTTPS Sender
* Validate `id` query parameter
* Content Modifier — build dynamic receiver path
* Request Reply — call JSONPlaceholder
* JSON to XML Converter
* Message Mapping — reshape response
* XML to JSON Converter
* End

Exception flow:

* Exception Subprocess
* GS Error Handler
* End

The main flow receives the `id` query parameter, validates it, calls the external receiver, converts and reshapes the response using standard CPI steps, and returns clean JSON.

The exception flow handles client-side input errors, receiver not-found responses, and receiver connectivity/technical failures.

## Validation Rules

* `id` is required
* `id` must be a positive integer (`>= 1`)
* Missing or invalid `id` returns HTTP `400`

## Field Mapping

| Source field   | Target field          |
|----------------|------------------------|
| `id`           | `customer.id`          |
| `name`         | `customer.name`        |
| `email`        | `customer.contactEmail`|
| `address.city` | `location.city`        |
| `company.name` | `organization.name`    |

Fields intentionally dropped: `username`, `phone`, `website`, `address.street`, `address.suite`, `address.zipcode`, `address.geo.lat`, `address.geo.lng`, `company.catchPhrase`, `company.bs`.

## Error Handling

* Missing or invalid `id` → HTTP `400`
* Receiver reachable but returns `404` (user not found) → HTTP `404`, passthrough — **not** treated as a backend error
* Receiver unreachable, timing out, or returning an unexpected/malformed response → HTTP `502`

This distinction matters: a `404` means the receiver worked correctly and reported that the resource doesn't exist. A `502` means the receiver interaction itself failed.

## Key Concepts

This project introduces:

* external receiver/API calls via Request Reply
* dynamic, request-driven receiver addressing
* standard JSON-to-XML and XML-to-JSON Converter steps
* Message Mapping for field dropping, renaming, and nesting
* minimal Groovy, scoped to structured error handling only
* correct separation of client, not-found, and backend error semantics

## Why This Matters

Real integrations call external systems that the integration developer doesn't control. Those systems return their own field names, structures, and status codes, which rarely match what the calling application needs. An integration flow has to reshape that response into a clean contract, and handle the receiver's own error responses (like `404`) differently from genuine connectivity or system failures.

This project also shows a shift from Groovy-based transformation (Projects 2 and 3) to CPI's standard converter and mapping tooling, used by default, with Groovy reserved for logic those steps can't express.

## Current Status

Project 4 is implemented and fully tested. All six test cases pass: missing/invalid/zero `id` (`400`), successful lookup and mapping (`200`), receiver `404` passthrough (`404`), and receiver unreachable (`502`).

Two implementation issues were found and fixed during testing:

* The original incoming query string was leaking onto the outgoing receiver URL (e.g. `.../users/1?id=1`) because `CamelHttpQuery` wasn't cleared after validation. Fixed by explicitly clearing that header in `id-validator.groovy`.
* The Message Mapping's root element (`response`) was initially leaking into the final JSON as a wrapper key. Fixed by enabling "Suppress JSON Root Element" on the XML-to-JSON Converter step.

## Files

* `id-validator.groovy` → validates the `id` query parameter and clears `CamelHttpQuery` before the receiver call
* `error-handler.groovy` → inspects receiver exceptions and distinguishes validation errors, not-found, and backend failures
* `source-schema.xsd` / `target-schema.xsd` → schemas used by the Message Mapping step
* `MM_UserLookup.mmap` → the Message Mapping definition reshaping the receiver response
* `test-cases.md` → documents the tested scenarios
* `CHANGELOG.md` → tracks project versions and improvements
* `screenshots/` → contains test evidence

## Screenshots

Each test case has two screenshots: request headers/status code, and response body.

* `01-missing-id-error-response.png` / `-02.png` → missing `id` handled with HTTP 400
* `02-invalid-id-nonnumeric-error-response.png` / `-02.png` → non-numeric `id` handled with HTTP 400
* `03-invalid-id-zero-error-response.png` / `-02.png` → `id=0` handled with HTTP 400
* `04-user-lookup-success-response.png` / `-02.png` → successful lookup and mapping with HTTP 200
* `05-receiver-404-user-not-found.png` / `-02.png` → receiver 404 passthrough
* `06-receiver-unreachable-502-error.png` / `-02.png` → receiver unreachable handled with HTTP 502
