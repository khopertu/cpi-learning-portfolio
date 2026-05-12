import com.sap.gateway.ip.core.customdev.util.Message
import java.net.URLDecoder

def Message processData(Message message) {
    def query = message.getHeader("CamelHttpQuery", String)

    def params = query?.split("&")?.collectEntries { pair ->
        def parts = pair.split("=", 2)
        def key = URLDecoder.decode(parts[0], "UTF-8")
        def value = parts.size() > 1 ? URLDecoder.decode(parts[1], "UTF-8") : ""
        [(key): value]
    }

    def name = params["name"]?.trim()
    def group = params["group"]?.trim()
    def mode = params["mode"]?.trim()
    def level = params["level"]?.trim()
    def source = params["source"]?.trim()
    def region = params["region"]?.trim()

    if (!name) {
        throw new Exception("Missing query param 'name'")
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
        throw new Exception("Invalid query param 'source'")
    }

    if (!region) {
        region = "EU"
    }

    if (!(region in ["EU", "NA", "APAC"])) {
        throw new Exception("Invalid query param 'region'")
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