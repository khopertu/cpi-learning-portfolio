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

- `01-xml-to-json-success-response.png`

---

## Planned Validation Tests

The following tests will be added after the first version is implemented:

### Missing Required Field

Example: missing `<name>`.

Expected result:

```text
HTTP 400
```

```json
{
  "error": "Missing required field 'name'"
}
```

### Malformed XML Body

Example: broken or incomplete XML.

Expected result:

```text
HTTP 400
```

```json
{
  "error": "Invalid XML body"
}
```

### Invalid Source Value

Example: `<source>desktop</source>`.

Expected result:

```text
HTTP 400
```

```json
{
  "error": "Invalid value for field 'source'"
}
```

### Invalid Region Value

Example: `<region>LATAM</region>`.

Expected result:

```text
HTTP 400
```

```json
{
  "error": "Invalid value for field 'region'"
}
```

## Notes

This project will validate the same business rules as the JSON to XML transformation project:

* `name` is required
* `source` is required
* `region` is required
* `source` must be one of: `web`, `mobile`, `api`
* `region` must be one of: `EU`, `NA`, `APAC`

The initial version will focus on transforming valid XML into JSON.
