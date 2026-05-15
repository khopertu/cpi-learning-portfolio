# HTTP Validation and Routing iFlow

## Overview

This is a practice SAP Integration Suite / CPI iFlow.

It receives HTTP input, validates fields, applies default values, derives internal properties, routes the message based on business logic, and returns JSON responses.

## Flow Design

Main flow:
- HTTPS Sender
- GS Parse
- Router
- Branch Content Modifiers
- Request Reply
- GS Response Builder
- End

Exception flow:
- Exception Subprocess
- GS Error Handler
- End

## Input Fields

- `name` → required
- `group` → optional, default = `"trial"`
- `mode` → optional, default = `"basic"`
- `level` → optional, default = `"junior"`
- `source` → optional, default = `"web"`
- `region` → optional, default = `"EU"`

## Derived Values

- `channel`
- `flowType`
- `priority`

## Example Requests

### Success

```text
/ping2?name=Alex
```

### Mobile route

```text
/ping2?name=Alex&source=mobile
```

### Invalid input

```text
/ping2?name=Alex&source=desktop
```

### Missing input

```text
/ping2?group=A
```
## Screenshots

The `screenshots/` folder contains evidence of the implemented iFlow design and runtime behavior:

- `01-iflow-canvas.png` → full iFlow canvas
- `02-router-mobile-route-condition.png` → Router condition using a Non-XML property expression
- `03-app-content-modifier-properties.png` → app branch exchange properties
- `04-default-content-modifier-properties.png` → default branch exchange properties
- `05-mobile-success-response.png` → successful mobile route response
- `06-invalid-source-error-response.png` → invalid input handled with HTTP 400
- `07-post-json-success-response.png` → successful POST request using JSON body input

## Test Evidence

The flow was tested with the following main scenarios:

- basic success request using default values
- mobile route request using `source=mobile`
- invalid source request returning `400`
- missing required `name` returning `400`

These tests confirm that the iFlow handles default values, input validation, Router branching, branch-specific properties, and structured JSON responses.

## What I Learned

- input validation in CPI
- Groovy parsing and response building
- Router and Content Modifier usage
- error handling with 400 vs 502
- tenant setup and deployment basics

## Current Status

This project currently uses query-parameter input and includes validation, default values, derived properties, Router-based branching, branch-specific properties, and JSON responses.

A planned next step is to extend the flow so it can also accept JSON body input while keeping the internal flow design stable.

## Versions

### Version 1.0 - Query Parameter Input

The first version of this iFlow supported HTTP input through query parameters.

Example:

```text
GET /ping2?name=Alex&source=mobile&region=EU
```

This version focused on:

- reading query parameters
- validating required and optional fields
- applying default values
- deriving internal properties
- routing based on `channel`
- setting branch-specific properties
- returning structured JSON responses
- handling input errors with HTTP `400`

### Version 1.1 - JSON Body Input Support

The iFlow was extended to support JSON request body input in addition to query parameters.

Example:

```http
POST /ping2
Content-Type: application/json
```

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "EU",
  "mode": "basic",
  "level": "junior"
}
```

In this version, the Parse Groovy script normalizes both input styles into the same internal properties.

This allows the Router, branch Content Modifiers, Request Reply, Response Builder, and Exception Subprocess to remain mostly stable.