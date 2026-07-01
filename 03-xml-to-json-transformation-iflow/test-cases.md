# Test Cases - XML to JSON Transformation iFlow

## Test 1 - XML to JSON Success Response

### Request

```http
POST /xml2json
Content-Type: application/xml
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

### Expected Result

```text
HTTP 200
```

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "EU"
}
```

### Result

The iFlow returns HTTP `200` and transforms the XML request body into a JSON response.

### Screenshot Evidence

* `01-xml-to-json-success-response.png`

---

## Test 2 - Missing Required Field

### Request

```http
POST /xml2json
Content-Type: application/xml
```

```xml
<request>
  <user>
  </user>
  <metadata>
    <source>mobile</source>
    <region>EU</region>
  </metadata>
</request>
```

### Expected Result

```text
HTTP 400
```

```json
{
  "error": "Missing required field 'name'"
}
```

### Result

The iFlow returns HTTP `400` because the required `name` field is missing from the XML request body.

### Screenshot Evidence

* `02-missing-required-field-error.png`

---

## Test 3 - Invalid Source Value

### Request

```http
POST /xml2json
Content-Type: application/xml
```

```xml
<request>
  <user>
    <name>Alex</name>
  </user>
  <metadata>
    <source>desktop</source>
    <region>EU</region>
  </metadata>
</request>
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

* `03-invalid-source-error-response.png`

---

## Test 4 - Invalid Region Value

### Request

```http
POST /xml2json
Content-Type: application/xml
```

```xml
<request>
  <user>
    <name>Alex</name>
  </user>
  <metadata>
    <source>mobile</source>
    <region>LATAM</region>
  </metadata>
</request>
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

* `04-invalid-region-error-response.png`

---

## Test 5 - Malformed XML Body

### Request

```http
POST /xml2json
Content-Type: application/xml
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
```

### Expected Result

```text
HTTP 400
```

```json
{
  "error": "Invalid XML body"
}
```

### Result

The iFlow returns HTTP `400` because the XML request body is malformed.

### Screenshot Evidence

* `05-invalid-xml-body-error-response.png`

---

## Test 6 - Nested JSON Success Response

### Request

```http
POST /xml2json
Content-Type: application/xml
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

### Expected Result

```text
HTTP 200
```

```json
{
  "user": {
    "name": "Alex"
  },
  "metadata": {
    "source": "mobile",
    "region": "EU"
  }
}
```

### Result

The iFlow returns HTTP `200` and transforms the XML request body into a nested JSON response.

The `name` field is grouped under `user`, while `source` and `region` are grouped under `metadata`.

### Screenshot Evidence

- `06-nested-json-success-response.png`

---

## Test 7 - Invalid Source After Nested JSON Update

### Request

```http
POST /xml2json
Content-Type: application/xml
```

```xml
<request>
  <user>
    <name>Alex</name>
  </user>
  <metadata>
    <source>desktop</source>
    <region>EU</region>
  </metadata>
</request>
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

The iFlow still returns HTTP `400` for invalid `source` values after the nested JSON output change.

This confirms that validation and error handling still work after changing the JSON response structure.

### Screenshot Evidence

- `07-invalid-source-after-nested-json.png`

## Notes

This project validates the same business rules as the JSON to XML transformation project:

* `name` is required
* `source` is required
* `region` is required
* `source` must be one of: `web`, `mobile`, `api`
* `region` must be one of: `EU`, `NA`, `APAC`

Client-side input errors return HTTP `400`.
