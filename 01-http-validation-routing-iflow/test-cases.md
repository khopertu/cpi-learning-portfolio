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
- error: `Invalid query param 'source'`

---

## 4) Missing required name case

Request:

```text
/ping2?group=A
```

Expected result:
- status code: `400`
- error: `Missing query param 'name'`

---

## 5) Invalid region case

Request:

```text
/ping2?name=Alex&region=LATAM
```

Expected result:
- status code: `400`
- error: `Invalid query param 'region'`

## Notes

These tests check:
- default values
- allowed-value validation
- Router branch behavior
- input error handling
