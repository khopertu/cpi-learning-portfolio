import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

def Message processData(Message message) {

    def reader = message.getBody(java.io.Reader)
    def parsedBody = new JsonSlurper().parse(reader)

    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)

    xml.request {
        name(parsedBody.name ?: "")
        source(parsedBody.source ?: "")
        region(parsedBody.region ?: "")
    }

    message.setHeader("Content-Type", "application/xml")
    message.setBody(writer.toString())

    return message
}