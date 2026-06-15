# Test Cases

## 1) POST JSON to XML success case

Request:

```http
POST /json2xml
Content-Type: application/json
```

Body:

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "EU"
}
```

Expected result:

* status code: `200`
* response `Content-Type`: `application/xml`
* XML root element: `request`
* `name` is mapped to `<name>Alex</name>`
* `source` is mapped to `<source>mobile</source>`
* `region` is mapped to `<region>EU</region>`

Expected response body:

```xml
<request>
  <name>Alex</name>
  <source>mobile</source>
  <region>EU</region>
</request>
```

Screenshot evidence:

* `01-post-json-to-xml-success-response.png`

---

## 2) Missing required field case

Request:

```http
POST /json2xml
Content-Type: application/json
```

Body:

```json
{
  "source": "mobile",
  "region": "EU"
}
```

Expected result:

* status code: `400`
* error: `Missing required field 'name'`

Screenshot evidence:

* `02-missing-required-field-error.png`

---

## 3) Malformed JSON body case

Request:

```http
POST /json2xml
Content-Type: application/json
```

Body:

```json
{"name":"Alex","source":}
```

Expected result:

* status code: `400`
* error: `Invalid JSON body`

Screenshot evidence:

* `03-invalid-json-body-error.png`

---

## Test 4 - Invalid Source Value

### Request

```http
POST /json2xml
Content-Type: application/json
```

```json
{
  "name": "Alex",
  "source": "desktop",
  "region": "EU"
}
```

### Expected Result

```text
HTTP 400
```

```json
{
  "error": "Invalid value for field 'source'"
}
```

### Result

The iFlow returns HTTP `400` because `desktop` is not an allowed value for the `source` field.

Allowed values:

* `web`
* `mobile`
* `api`

### Screenshot Evidence

* `04-invalid-source-error-response.png`

---

## Test 5 - Invalid Region Value

### Request

```http
POST /json2xml
Content-Type: application/json
```

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "LATAM"
}
```

### Expected Result

```text
HTTP 400
```

```json
{
  "error": "Invalid value for field 'region'"
}
```

### Result

The iFlow returns HTTP `400` because `LATAM` is not an allowed value for the `region` field.

Allowed values:

* `EU`
* `NA`
* `APAC`

### Screenshot Evidence

* `05-invalid-region-error-response.png`

## Test 6 - Nested XML Success Response

### Request

```http
POST /json2xml
Content-Type: application/json
```

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "EU"
}
```

### Expected Result

```text
HTTP 200
```

```xml
<request>
  <user>
    <name>Alex</name>
  </user>
  <metadata>
    <source>mobile</source>
    <region>EU</region>
  </metadata>
</request>
```

### Result

The iFlow returns HTTP `200` and transforms the JSON request body into a nested XML response.

The `name` field is grouped under `user`, while `source` and `region` are grouped under `metadata`.

### Screenshot Evidence

- `06-nested-xml-success-response.png`

---

## Test 7 - Invalid Source After Nested XML Update

### Request

```http
POST /json2xml
Content-Type: application/json
```

```json
{
  "name": "Alex",
  "source": "desktop",
  "region": "EU"
}
```

### Expected Result

```text
HTTP 400
```

```json
{
  "error": "Invalid value for field 'source'"
}
```

### Result

The iFlow still returns HTTP `400` for invalid `source` values after the nested XML output change.

This confirms that validation and error handling still work after changing the XML response structure.

### Screenshot Evidence

- `07-invalid-source-after-nested-xml.png`

## Notes

These tests check:

* JSON request body input
* JSON to XML message transformation
* XML response generation
* required field validation
* malformed JSON handling
* client-side input errors returning HTTP `400`

Project 2.0 introduced the basic JSON to XML transformation.

Project 2.1 added validation and error handling for missing required fields and malformed JSON input.
