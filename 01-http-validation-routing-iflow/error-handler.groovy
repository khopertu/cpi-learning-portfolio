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

    def missingMatch = rawMsg =~ /Missing query param '[^']+'/
    def invalidMatch = rawMsg =~ /Invalid query param '[^']+'/

    if (missingMatch.find()) {
        def cleanMsg = missingMatch.group(0)
        message.setHeader("CamelHttpResponseCode", 400)
        message.setBody("{\"error\":\"${cleanMsg}\",\"timestamp\":\"${ts}\",\"mplId\":\"${mplId}\"}")
    } else if (invalidMatch.find()) {
        def cleanMsg = invalidMatch.group(0)
        message.setHeader("CamelHttpResponseCode", 400)
        message.setBody("{\"error\":\"${cleanMsg}\",\"timestamp\":\"${ts}\",\"mplId\":\"${mplId}\"}")
    } else {
        message.setHeader("CamelHttpResponseCode", 502)
        message.setBody("{\"error\":\"Backend system error\",\"timestamp\":\"${ts}\",\"mplId\":\"${mplId}\"}")
    }

    return message
}