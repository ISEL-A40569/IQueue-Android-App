package isel.leic.ps.iqueue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServiceQueue (
    val serviceQueueId: Int,
    val serviceQueueDescription: String
) : Parcelable