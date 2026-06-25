import com.sap.gateway.ip.core.customdev.util.Message
import groovy.util.XmlSlurper
import groovy.json.JsonBuilder

def Message processData(Message message) {

    def body = message.getBody(String)

    def xml = new XmlSlurper(false, false).parseText(body)

    def nameValue = xml.user.name.text()?.trim()
    def sourceValue = xml.metadata.source.text()?.trim()
    def regionValue = xml.metadata.region.text()?.trim()

    def json = new JsonBuilder([
        name  : nameValue,
        source: sourceValue,
        region: regionValue
    ])

    message.setHeader("Content-Type", "application/json")
    message.setBody(json.toPrettyString())

    return message
}