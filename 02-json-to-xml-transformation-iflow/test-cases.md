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

---

## Notes

This first version checks the basic transformation behavior:

* JSON request body is received
* JSON fields are parsed
* XML response body is generated
* JSON field names become XML elements
* JSON field values become XML element values

Error handling and validation will be added in a later version.
