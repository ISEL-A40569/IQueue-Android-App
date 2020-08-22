package isel.leic.ps.iqueue.model

data class EddystoneUid (
    val eddystoneUid: String
) {
    val namespaceId: String = eddystoneUid.substring(0, 20)
    val instanceId: String = eddystoneUid.substring(20, 32)

}