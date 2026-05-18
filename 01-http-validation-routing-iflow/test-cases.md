# Test Cases

Screenshots for selected test results are available in the `screenshots/` folder.

## 1) Basic success case

Request:

```text
/ping2?name=Alex
```

Expected result:
- status code: `200`
- `group = "trial"`
- `mode = "basic"`
- `level = "junior"`
- `source = "web"`
- `region = "EU"`
- `channel = "standard"`
- `flowType = "default-path"`
- `priority = "normal"`

---

## 2) Mobile route case

Request:

```text
/ping2?name=Alex&source=mobile
```

Expected result:
- status code: `200`
- `source = "mobile"`
- `channel = "app"`
- `flowType = "mobile-path"`
- `priority = "high"`

---

## 3) Invalid source case

Request:

```text
/ping2?name=Alex&source=desktop
```

Expected result:
- status code: `400`
- error: `Invalid value for field 'source'`

---

## 4) Missing required name case

Request:

```text
/ping2?group=A
```

Expected result:
- status code: `400`
- error: `Missing required field 'name'`

---

## 5) Invalid region case

Request:

```text
/ping2?name=Alex&region=LATAM
```

Expected result:
- status code: `400`
- error: `Invalid value for field 'region'`

## 6) POST JSON body success case

Request:

```http
POST /ping2
Content-Type: application/json
```

Body:

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "EU",
  "mode": "basic",
  "level": "junior"
}
```

Expected result:
- status code: `200`
- `name = "Alex"`
- `source = "mobile"`
- `region = "EU"`
- `channel = "app"`
- `flowType = "mobile-path"`
- `priority = "high"`

## 7) Malformed JSON body case

Request:

```http
POST /ping2
Content-Type: application/json
```

Body:

```json
{"name":"Alex","source":}
```

Expected result:
- status code: `400`
- error: `Invalid JSON body`

=======
## Notes

These tests check:
- default values
- allowed-value validation
- Router branch behavior
- input error handling
