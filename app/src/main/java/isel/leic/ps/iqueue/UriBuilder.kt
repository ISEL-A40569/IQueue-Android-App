package isel.leic.ps.iqueue

class UriBuilder {
    private val protocol = "https"
    private val host = "192.168.1.245"
    private val port = "8443"
    private val commonPath = "/api/iqueue/"

    private fun buildUri(variablePath: String) : String {
        return "${protocol}://${host}:${port}${commonPath}${variablePath}"
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

    fun getAttendanceTicketUri(attendanceId: Int) : String {
        return buildUri("attendance/${attendanceId}/ticket")
    }

    fun getAttendancesUri() : String {
        return buildUri("attendance")
    }

    fun getAttendanceUri(attendanceId: Int) : String {
        return buildUri("attendance/${attendanceId}")
    }

    fun getCurrentAttendanceUri(serviceQueueId: Int) : String {
        return buildUri("servicequeue/${serviceQueueId}/currentattendance")
    }

    fun getBeaconEddystoneUidUri() : String {
        return buildUri("beacon/eddystoneUid")
    }

    fun getDeskUri(deskId: Int) : String {
        return buildUri("desk/${deskId}")
    }

    fun getUserCredentialsUri(userId: Int) : String {
        return buildUri("user/${userId}/credentials")
    }

    fun getAttendanceClassificationUri() : String {
        return buildUri("attendance/classification")
    }
}