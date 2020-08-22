package isel.leic.ps.iqueue.model

data class AttendanceClassification(
    val attendanceId: Int,
    val classificationCreationDateTime: String,
    val rate: Int,
    val observations: String?
)
