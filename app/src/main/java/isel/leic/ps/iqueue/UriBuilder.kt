package isel.leic.ps.iqueue

class UriBuilder {
    private val protocol = "https://"
    private val host = "192.168.1.245"
    private val port = ":8443"
    private val commonPath = "/api/iqueue/"

    private fun buildUri(variablePath: String) : String {
        return "${protocol}${host}${port}${commonPath}${variablePath}"
    }

    fun getLoginUri() : String {
        return buildUri("login")
    }

    fun getUserUri() : String {
        return buildUri("user")
    }

    fun getOperatorServiceQueuesUri(operatorId: Int) : String {
        return buildUri("servicequeue?operatorId=${operatorId}")
    }

    fun getOperatorUri() : String {
        return buildUri("operator")
    }

    fun getAttendanceUri() : String {
        return buildUri("attendance")
    }

    fun getBeaconEddystoneUidUri() : String {
        return buildUri("beacon/eddystoneUid")
    }

    fun getServiceQueueUri() : String {
        return buildUri("servicequeue")
    }

    fun getDeskUri() : String {
        return buildUri("desk")
    }
}