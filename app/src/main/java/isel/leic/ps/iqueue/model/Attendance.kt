package isel.leic.ps.iqueue.model

import java.time.LocalDateTime

data class Attendance (
    val serviceQueueId: Int,
    var deskId: Int?,
    val clientId: Int,
    val startWaitingDateTime: String,
    var startAttendanceDateTime: String?,
    var endAttendanceDateTime: String?,
    var attendanceStatusId: Int
)
