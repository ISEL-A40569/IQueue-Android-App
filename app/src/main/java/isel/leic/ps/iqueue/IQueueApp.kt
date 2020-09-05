package isel.leic.ps.iqueue

import android.app.Application
import android.content.Intent
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.nearby.Nearby.getMessagesClient
import com.google.android.gms.nearby.messages.*
import com.google.gson.Gson
import isel.leic.ps.iqueue.model.Attendance
import org.json.JSONObject
import kotlin.concurrent.thread


class IQueueApp : Application() {

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    val gson = Gson()

    var userId: Int? = null

    @Volatile
    var isOnBeaconReach: Boolean = false

    val ticketsLeftWarningLimit = 3

    var attendance: Attendance? = null

    @Volatile
    var isLoggedIn: Boolean = false

    var uriBuilder: UriBuilder? = null

    override fun onCreate() {
        super.onCreate()
        uriBuilder = UriBuilder()
    }

    override fun onTerminate() {
        super.onTerminate()
        isLoggedIn = false
//        messagesClient!!.unsubscribe(messageListener!!)
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

var Application.isOnBeaconReach: Boolean
    get() = (this as IQueueApp).isOnBeaconReach
    set(value) {
        (this as IQueueApp).isOnBeaconReach = value
    }

val Application.ticketsLeftWarningLimit: Int
    get() = (this as IQueueApp).ticketsLeftWarningLimit

var Application.attendance: Attendance?
    get() = (this as IQueueApp).attendance
    set(value) {
        (this as IQueueApp).attendance = value
    }

//val Application.messagesClient: MessagesClient
//    get() = (this as IQueueApp).messagesClient!!
//
//val Application.messagesListener: MessageListener
//    get() = (this as IQueueApp).messageListener!!
//
//val Application.subscribeOptions: SubscribeOptions
//    get() = (this as IQueueApp).subscribeOptions!!

var Application.isLoggedIn: Boolean
    get() = (this as IQueueApp).isLoggedIn
    set(value) {(this as IQueueApp).isLoggedIn = value}

val Application.uriBuilder: UriBuilder?
    get() = (this as IQueueApp).uriBuilder