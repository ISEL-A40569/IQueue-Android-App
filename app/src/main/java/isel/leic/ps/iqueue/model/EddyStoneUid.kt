package isel.leic.ps.iqueue.model

//data class EddyStoneUid (
//    val eddyStoneUid: String
//) {
//    val namespaceId: String = eddyStoneUid.substring(0, 20)
//    val instanceId: String = eddyStoneUid.substring(20, 32)
//
//}

data class EddyStoneUid(
    val namespaceId: String,
    val instanceId: String
)