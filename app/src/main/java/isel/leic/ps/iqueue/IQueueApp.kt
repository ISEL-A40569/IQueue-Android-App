package isel.leic.ps.iqueue

import android.app.Application
import android.content.Intent
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import isel.leic.ps.iqueue.model.Attendance

class IQueueApp : Application() {

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    val gson = Gson()

    var userId: Int? = null

    @Volatile
    var canScanBeacons: Boolean = true

    val ticketsLeftWarningLimit = 3

    var attendance: Attendance? = null

}

val Application.requestQueue: RequestQueue
    get() = (this as IQueueApp).requestQueue

val Application.gson: Gson
    get() = (this as IQueueApp).gson

var Application.userId: Int?
    get() = (this as IQueueApp).userId
    set(value) {
        (this as IQueueApp).userId = value
    }

var Application.canScanBeacons: Boolean
    get() = (this as IQueueApp).canScanBeacons
    set(value) {
        (this as IQueueApp).canScanBeacons = value
    }

val Application.ticketsLeftWarningLimit: Int
    get() = (this as IQueueApp).ticketsLeftWarningLimit

var Application.attendance: Attendance?
    get() = (this as IQueueApp).attendance
    set(value) {
        (this as IQueueApp).attendance = value
    }