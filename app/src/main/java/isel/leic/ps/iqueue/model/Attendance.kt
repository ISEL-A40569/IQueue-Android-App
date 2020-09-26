package isel.leic.ps.iqueue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attendance(
    var attendanceId: Int?,
    val serviceQueueId: Int,
    var deskId: Int?,
    val clientId: Int,
    val startWaitingDateTime: String,
    var startAttendanceDateTime: String?,
    var endAttendanceDateTime: String?,
    var attendanceStatusId: Int
) : Parcelable
