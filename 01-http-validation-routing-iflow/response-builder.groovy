import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonBuilder

def Message processData(Message message) {
    def name = message.getProperty("name")
    def group = message.getProperty("group")
    def mode = message.getProperty("mode")
    def level = message.getProperty("level")
    def source = message.getProperty("source")
    def region = message.getProperty("region")
    def channel = message.getProperty("channel")
    def flowType = message.getProperty("flowType")
    def priority = message.getProperty("priority")

    def body = new JsonBuilder([
        name: name,
        group: group,
        mode: mode,
        level: level,
        source: source,
        region: region,
        channel: channel,
        flowType: flowType,
        priority: priority
    ]).toPrettyString()

    message.setHeader("Content-Type", "application/json")
    message.setBody(body)

    return message
}