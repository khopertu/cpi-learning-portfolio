# Test Cases - External Receiver Call and Standard Mapping iFlow

## Test 1 - Missing Required Query Parameter

### Request

```http
GET /user-lookup
```

### Expected Result

```text
HTTP 400
```

```json
{
  "error": "Missing required query param 'id'"
}
```

### Result

Confirmed. The iFlow returns HTTP `400` with the expected error message when the `id` query parameter is missing entirely.

### Screenshot Evidence

* `01-missing-id-error-response.png` — request headers and status code
* `01-missing-id-error-response-02.png` — response body

---

## Test 2 - Invalid Query Parameter (Non-Numeric)

### Request

```http
GET /user-lookup?id=abc
```

### Expected Result

```text
HTTP 400
```

```json
{
  "error": "Invalid query param 'id'"
}
```

### Result

Confirmed. The iFlow returns HTTP `400` with the expected error message when `id` is not numeric.

### Screenshot Evidence

* `02-invalid-id-nonnumeric-error-response.png` — request headers and status code
* `02-invalid-id-nonnumeric-error-response-02.png` — response body

---

## Test 3 - Invalid Query Parameter (Zero)

### Request

```http
GET /user-lookup?id=0
```

### Expected Result

```text
HTTP 400
```

```json
{
  "error": "Invalid query param 'id'"
}
```

### Result

Confirmed. The iFlow returns HTTP `400` for `id=0`, proving the validation checks for a positive integer rather than just any numeric value.

### Screenshot Evidence

* `03-invalid-id-zero-error-response.png` — request headers and status code
* `03-invalid-id-zero-error-response-02.png` — response body

---

## Test 4 - Successful Lookup and Mapping

### Request

```http
GET /user-lookup?id=1
```

### Expected Result

```text
HTTP 200
```

```json
{
  "customer": {
    "id": "1",
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

Note: `id` is returned as a string, not a number. This is expected given both schemas type it as `xs:string` and the value flows through as text the entire way (query parameter → XML → JSON).

### Result

Confirmed. The iFlow returns HTTP `200` with the correctly reshaped, nested response. The receiver call, JSON-to-XML conversion, Message Mapping, and XML-to-JSON conversion all work end to end, and none of the intentionally dropped fields (`username`, `phone`, `website`, `address.street`, `address.suite`, `address.zipcode`, `address.geo.lat`, `address.geo.lng`, `company.catchPhrase`, `company.bs`) appear in the response.

### Screenshot Evidence

* `04-user-lookup-success-response.png` — request headers and status code
* `04-user-lookup-success-response-02.png` — response body

---

## Test 5 - Receiver 404 Passthrough

### Request

```http
GET /user-lookup?id=999
```

### Expected Result

```text
HTTP 404
```

```json
{
  "error": "User not found"
}
```

### Result

Confirmed. The iFlow returns HTTP `404` with a "User not found" body when the receiver correctly reports the user doesn't exist, per the Receiver 404 Handling Decision in the README. The Error Handler extracts the real status code from `CamelExceptionCaught`'s message (`AhcOperationFailedException`, containing `statusCode: 404`) and passes it through instead of defaulting to `502`.

### Screenshot Evidence

* `05-receiver-404-user-not-found.png` — request headers and status code
* `05-receiver-404-user-not-found-02.png` — response body

---

## Test 6 - Receiver Unreachable

### Request

```http
GET /user-lookup?id=1
```

Receiver endpoint temporarily pointed at an invalid/unreachable hostname.

### Expected Result

```text
HTTP 502
```

```json
{
  "error": "Backend system error"
}
```

### Result

Confirmed. With the receiver hostname temporarily pointed at an unreachable address, the iFlow returns HTTP `502` with a "Backend system error" body, correctly distinguishing a genuine connectivity failure from a receiver `404`.

### Screenshot Evidence

* `06-receiver-unreachable-502-error.png` — request headers and status code
* `06-receiver-unreachable-502-error-02.png` — response body

## Notes

This project validates the following business rules:

* `id` is required
* `id` must be a positive integer (`>= 1`)
* A receiver `404` is passed through as `404`, not converted to `502`
* A receiver connectivity or technical failure returns `502`

Client-side input errors return HTTP `400`.
