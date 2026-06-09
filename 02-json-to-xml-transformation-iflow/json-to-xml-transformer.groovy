import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

def Message processData(Message message) {

    def parsedBody

    try {
        def reader = message.getBody(java.io.Reader)
        parsedBody = new JsonSlurper().parse(reader)

        if (!(parsedBody instanceof Map)) {
            throw new Exception("Invalid JSON body")
        }

    } catch (Exception e) {
        throw new Exception("Invalid JSON body")
    }

    def nameValue = parsedBody.name?.toString()?.trim()
    def sourceValue = parsedBody.source?.toString()?.trim()
    def regionValue = parsedBody.region?.toString()?.trim()

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
	
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)

    xml.request {
        name(nameValue)
        source(sourceValue)
        region(regionValue)
    }

    message.setHeader("Content-Type", "application/xml")
    message.setBody(writer.toString())

    return message
}