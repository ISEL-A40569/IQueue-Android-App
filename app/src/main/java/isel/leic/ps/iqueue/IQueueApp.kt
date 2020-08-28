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


class IQueueApp : Application() {

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    val gson = Gson()

    var userId: Int? = null

    @Volatile
    var isOnBeaconReach: Boolean = false

    val ticketsLeftWarningLimit = 3

    var attendance: Attendance? = null

    var messagesClient: MessagesClient? = null

    var messageListener: MessageListener? = null

    var subscribeOptions: SubscribeOptions? = null

    override fun onCreate() {
        super.onCreate()
        messagesClient = getMessagesClient(this)

        messageListener = createMessageListener()

        subscribeOptions = getNearbySubscriptionOptions()
    }

    override fun onTerminate() {
        super.onTerminate()
        messagesClient!!.unsubscribe(messageListener!!)
    }

    private fun createMessageListener(): MessageListener {
        return object : MessageListener() {
            override fun onFound(message: Message?) {
                super.onFound(message)
                val eddystoneUid =
                    isel.leic.ps.iqueue.model.EddystoneUid(byteArrayToHex(message!!.content))
                Log.d("TEST: ", gson.toJson(eddystoneUid).toString())

                isOnBeaconReach = true
                makeBeaconEddystoneUidRequest(eddystoneUid)
            }

            override fun onLost(message: Message?) {
                Log.d("TEST: ", "On Lost Message")
                isOnBeaconReach = false
//                messagesClient!!.unsubscribe(this)
            }
        }
    }


    private fun byteArrayToHex(bytes: ByteArray): String {
        val stringBuilder = StringBuilder(bytes.size * 2)
        for (byte in bytes)
            stringBuilder.append(String.format("%02x", byte))
        return stringBuilder.toString()
    }

    private fun getNearbySubscriptionOptions(): SubscribeOptions {
        return SubscribeOptions.Builder()
            .setStrategy(Strategy.BLE_ONLY)
            .setFilter(
                MessageFilter.Builder()
                    .includeEddystoneUids(
                        "00112233445566778899",
                        "abcde0eb00a0"
                    )   // TODO: should obtain this from API
                    .build()
            )
            .build()
    }

    private fun makeBeaconEddystoneUidRequest(eddystoneUid: isel.leic.ps.iqueue.model.EddystoneUid) {
        requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.245:8080/api/iqueue/beacon/eddystoneUid",
                JSONObject(gson.toJson(eddystoneUid).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                    startServiceQueuesActivity(response.getInt("operatorId"))
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }


    private fun startServiceQueuesActivity(operatorId: Int) {
        val intent =
            Intent(applicationContext, ServiceQueuesActivity::class.java)
        intent.putExtra("operatorId", operatorId)

        startActivity(intent)
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

val Application.messagesClient: MessagesClient
    get() = (this as IQueueApp).messagesClient!!

val Application.messagesListener: MessageListener
    get() = (this as IQueueApp).messageListener!!

val Application.subscribeOptions: SubscribeOptions
    get() = (this as IQueueApp).subscribeOptions!!