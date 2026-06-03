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
