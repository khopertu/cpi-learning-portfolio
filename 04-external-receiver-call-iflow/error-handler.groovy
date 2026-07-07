import com.sap.gateway.ip.core.customdev.util.Message
import java.time.Instant

def Message processData(Message message) {

    message.setHeader("Content-Type", "application/json")

    def headers = message.getHeaders()
    def ts = Instant.now().toString()

    def ex = message.getProperty("CamelExceptionCaught")
    def rawMsg = ex?.getMessage() ?: ""

    def mplId = (headers.get("sap_messageprocessinglogid")
            ?: headers.get("Sap_messageprocessinglogid")
            ?: headers.get("SAP_MessageProcessingLogID")
            ?: "")

    def missingIdMatch = rawMsg.contains("Missing required query param 'id'")
    def invalidIdMatch = rawMsg.contains("Invalid query param 'id'")
    def statusCodeMatch = rawMsg =~ /statusCode:\s*(\d+)/

    if (missingIdMatch) {
        message.setHeader("CamelHttpResponseCode", 400)
        message.setBody("{\"error\":\"Missing required query param 'id'\",\"timestamp\":\"${ts}\",\"mplId\":\"${mplId}\"}")
    } else if (invalidIdMatch) {
        message.setHeader("CamelHttpResponseCode", 400)
        message.setBody("{\"error\":\"Invalid query param 'id'\",\"timestamp\":\"${ts}\",\"mplId\":\"${mplId}\"}")
    } else if (statusCodeMatch.find() && statusCodeMatch.group(1) == "404") {
        message.setHeader("CamelHttpResponseCode", 404)
        message.setBody("{\"error\":\"User not found\",\"timestamp\":\"${ts}\",\"mplId\":\"${mplId}\"}")
    } else {
        message.setHeader("CamelHttpResponseCode", 502)
        message.setBody("{\"error\":\"Backend system error\",\"timestamp\":\"${ts}\",\"mplId\":\"${mplId}\"}")
    }

    return message
}
