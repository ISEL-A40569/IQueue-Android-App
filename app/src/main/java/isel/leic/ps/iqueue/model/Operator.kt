package isel.leic.ps.iqueue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class Operator (
    val operatorId: Int,
    val operatorDescription: String
) //: Parcelable