package isel.leic.ps.iqueue

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import isel.leic.ps.iqueue.model.Attendance
import isel.leic.ps.iqueue.utils.ActivityStarter
import isel.leic.ps.iqueue.utils.UriBuilder


class IQueueApp : Application() {

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    val gson = Gson()

    val ticketsLeftWarningLimit = 3

    var userId: Int? = null

    @Volatile
    var isOnBeaconReach = false

    @Volatile
    var attendance: Attendance? = null

    var uriBuilder: UriBuilder? = null

    var activityStarter: ActivityStarter? = null

    override fun onCreate() {
        super.onCreate()
        uriBuilder = UriBuilder()
        activityStarter = ActivityStarter()
    }

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

val Application.ticketsLeftWarningLimit: Int
    get() = (this as IQueueApp).ticketsLeftWarningLimit

var Application.attendance: Attendance?
    get() = (this as IQueueApp).attendance
    set(value) {
        (this as IQueueApp).attendance = value
    }

var Application.isOnBeaconReach: Boolean
    get() = (this as IQueueApp).isOnBeaconReach
    set(value) {
        (this as IQueueApp).isOnBeaconReach = value
    }

val Application.uriBuilder: UriBuilder?
    get() = (this as IQueueApp).uriBuilder

val Application.activityStarter: ActivityStarter?
    get() = (this as IQueueApp).activityStarter


