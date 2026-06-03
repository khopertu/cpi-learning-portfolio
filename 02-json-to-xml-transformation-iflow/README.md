# JSON to XML Transformation iFlow

## Overview

This is a practice SAP Integration Suite / CPI iFlow focused on message transformation.

The goal of this project is to receive JSON input, transform it into XML, and return the transformed XML response.

This project builds on the previous HTTP validation and routing iFlow by moving into a new integration topic: converting data from one message format to another.

## Goal

The goal of this iFlow is to practice:

- receiving JSON input
- reading fields from a JSON request body
- transforming JSON into XML
- returning XML as the response body
- understanding why message transformation is useful in integration scenarios

## Example Input

```json
{
  "name": "Alex",
  "source": "mobile",
  "region": "EU"
}
```

## Example Output

```xml
<request>
  <name>Alex</name>
  <source>mobile</source>
  <region>EU</region>
</request>
```

## Flow Design

Planned main flow:

- HTTPS Sender
- GS JSON to XML Transformer
- End

Possible later extensions:

- input validation
- error handling
- support for missing fields
- XML-to-JSON transformation
- integration with a receiver system

## Key Concepts

This project introduces:

- JSON structure
- XML structure
- root elements
- XML elements/tags
- message transformation
- request body processing
- response body formatting

## Why This Matters

In real integration scenarios, different systems often expect different message formats.

One system may send JSON, while another system expects XML. An integration flow can sit between those systems and transform the message so the receiving system can process it correctly.

## Current Status

Project 2.0 introduced the basic JSON to XML transformation.

Project 2.1 added validation and error handling for missing required fields and malformed JSON body input.