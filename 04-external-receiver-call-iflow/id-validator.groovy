import com.sap.gateway.ip.core.customdev.util.Message
import java.net.URLDecoder

def Message processData(Message message) {
    def query = message.getHeader("CamelHttpQuery", String)

    def params = query?.trim()
            ? query.split("&").collectEntries { pair ->
                def parts = pair.split("=", 2)
                def key = URLDecoder.decode(parts[0], "UTF-8")
                def value = parts.size() > 1 ? URLDecoder.decode(parts[1], "UTF-8") : ""
                [(key): value]
            }
            : [:]

    def idValue = params["id"]?.trim()

    if (!idValue) {
        throw new Exception("Missing required query param 'id'")
    }

    if (!idValue.isInteger() || idValue.toInteger() < 1) {
        throw new Exception("Invalid query param 'id'")
    }

    message.setProperty("userId", idValue)
    message.setHeader("CamelHttpQuery", null)

    return message
}
