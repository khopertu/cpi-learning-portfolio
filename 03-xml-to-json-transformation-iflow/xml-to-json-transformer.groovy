import com.sap.gateway.ip.core.customdev.util.Message
import groovy.util.XmlSlurper
import groovy.json.JsonBuilder

def Message processData(Message message) {

    def parsedXml

    try {
        def body = message.getBody(String)
        parsedXml = new XmlSlurper(false, false).parseText(body)
    } catch (Exception e) {
        throw new Exception("Invalid XML body")
    }

    def nameValue = parsedXml.user.name.text()?.trim()
    def sourceValue = parsedXml.metadata.source.text()?.trim()
    def regionValue = parsedXml.metadata.region.text()?.trim()

    if (!nameValue) {
        throw new Exception("Missing required field 'name'")
    }

    if (!sourceValue) {
        throw new Exception("Missing required field 'source'")
    }

    if (!regionValue) {
        throw new Exception("Missing required field 'region'")
    }

    def allowedSources = ["web", "mobile", "api"]
    def allowedRegions = ["EU", "NA", "APAC"]

    if (!(sourceValue in allowedSources)) {
        throw new Exception("Invalid value for field 'source'")
    }

    if (!(regionValue in allowedRegions)) {
        throw new Exception("Invalid value for field 'region'")
    }

    def json = new JsonBuilder([
        name  : nameValue,
        source: sourceValue,
        region: regionValue
    ])

    message.setHeader("Content-Type", "application/json")
    message.setBody(json.toPrettyString())

    return message
}