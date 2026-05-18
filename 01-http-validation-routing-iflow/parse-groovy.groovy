import com.sap.gateway.ip.core.customdev.util.Message
import java.net.URLDecoder
import groovy.json.JsonSlurper

def Message processData(Message message) {
    def query = message.getHeader("CamelHttpQuery", String)
    def method = message.getHeader("CamelHttpMethod", String) ?: ""
    def headers = message.getHeaders()
    
    def contentType = (
            message.getHeader("Content-Type", String)
            ?: message.getHeader("content-type", String)
            ?: headers.find { k, v -> k?.equalsIgnoreCase("Content-Type") }?.value
            ?: ""
    ).toString()

    def params = [:]
    
    if (method.equalsIgnoreCase("POST") || contentType.toLowerCase().contains("application/json")) {
        try {
            def reader = message.getBody(java.io.Reader)
            def parsedBody = new JsonSlurper().parse(reader)
    
            if (!(parsedBody instanceof Map)) {
                throw new Exception("Invalid JSON body")
            }
    
            params = parsedBody.collectEntries { key, value ->
                [(key.toString()): value == null ? "" : value.toString()]
            }
    
        } catch (Exception e) {
            throw new Exception("Invalid JSON body")
        }
    } else {
        params = query?.trim()
                ? query.split("&").collectEntries { pair ->
                    def parts = pair.split("=", 2)
                    def key = URLDecoder.decode(parts[0], "UTF-8")
                    def value = parts.size() > 1 ? URLDecoder.decode(parts[1], "UTF-8") : ""
                    [(key): value]
                }
                : [:]
    }

    def name = params["name"]?.trim()
    def group = params["group"]?.trim()
    def mode = params["mode"]?.trim()
    def level = params["level"]?.trim()
    def source = params["source"]?.trim()
    def region = params["region"]?.trim()

    if (!name) {
        throw new Exception("Missing required field 'name'")
    }

    if (!group) {
        group = "trial"
    }

    if (!mode) {
        mode = "basic"
    }

    if (!level) {
        level = "junior"
    }

    if (!source) {
        source = "web"
    }

    if (!(source in ["web", "mobile", "api"])) {
        throw new Exception("Invalid value for field 'source'")
    }

    if (!region) {
        region = "EU"
    }

    if (!(region in ["EU", "NA", "APAC"])) {
        throw new Exception("Invalid value for field 'region'")
    }

    def channel

    if (source == "mobile") {
        channel = "app"
    } else {
        channel = "standard"
    }

    message.setProperty("name", name)
    message.setProperty("group", group)
    message.setProperty("mode", mode)
    message.setProperty("level", level)
    message.setProperty("source", source)
    message.setProperty("region", region)
    message.setProperty("channel", channel)

    return message
}