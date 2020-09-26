package isel.leic.ps.iqueue


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.*
import isel.leic.ps.iqueue.model.EddyStoneUid
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        startScanBeaconsThread()
    }

    fun onChangePassword(view: View) {
        application.activityStarter!!.startChangePasswordActivity(this)
    }

    fun onGetOperators(view: View) {
        application.activityStarter!!.startOperatorsActivity(this)
    }

    fun onCheckTicket(view: View) {
        if (application.attendance != null) {
            application.activityStarter!!.startCurrentTicketActivity(this)
        } else {
            showNoTicketsMessage()
        }
    }

    private fun startScanBeaconsThread() {
        thread {
            makeGetBeaconsRequest()
        }
    }

    private fun scanBeacons(subscribeOptions: SubscribeOptions) {
        val messagesClient = Nearby.getMessagesClient(this)
        val messagesListener = createMessageListener()
        messagesClient.subscribe(messagesListener, subscribeOptions)
    }

    private fun createMessageListener(): MessageListener {
        return object : MessageListener() {
            override fun onFound(message: Message?) {
                Log.d("TEST: ", "onFound")
                val nearbyEddyStoneUid = EddystoneUid.from(message)
                if (!application.isOnBeaconReach) {
                    makeBeaconEddyStoneUidRequest(
                        EddyStoneUid(
                            nearbyEddyStoneUid.namespace,nearbyEddyStoneUid.instance)
                    )
                }
            }

            override fun onLost(message: Message?) {
                Log.d("TEST: ", "onLost")
                if (application!!.attendance != null) {
                    application.activityStarter!!
                        .startContinueWaitingConfirmationActivity(applicationContext)
                }
                application.isOnBeaconReach = false
            }
        }
    }

    private fun getNearbySubscriptionOptions(messageFilter: MessageFilter): SubscribeOptions {
        return SubscribeOptions.Builder()
            .setStrategy(Strategy.BLE_ONLY)
            .setFilter(messageFilter)
            .build()
    }

    private fun makeBeaconEddyStoneUidRequest(eddyStoneUid: EddyStoneUid) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                application!!.uriBuilder!!.getBeaconEddystoneUidUri(),
                JSONObject(application.gson.toJson(eddyStoneUid).toString()),
                Response.Listener<JSONObject> { response ->
                    application.isOnBeaconReach = true

                    if (application.attendance == null)
                        application.activityStarter!!
                            .startServiceQueuesActivity(
                                applicationContext,
                                response.getInt("operatorId")
                            )
                },
                Response.ErrorListener { error ->
                })
        )
    }

    private fun showNoTicketsMessage() {

        Toast.makeText(this, getString(R.string.no_tickets_message), Toast.LENGTH_SHORT)
            .show()
    }

    private fun makeGetBeaconsRequest() {
        val messageFilterBuilder = MessageFilter.Builder()
        application.requestQueue.add(
            JsonArrayRequest(
                Request.Method.GET,
                application.uriBuilder!!.getBeaconsUri(),
                null,
                Response.Listener<JSONArray> { response ->
                    var index = 0
                    while (index < response.length()) {
                        val eddyStoneUid = application.gson.fromJson(
                            response[index].toString(),
                            EddyStoneUid::class.java
                        )
                        messageFilterBuilder.includeEddystoneUids(
                            eddyStoneUid.namespaceId,
                            eddyStoneUid.instanceId
                        )
                        ++index
                    }
                    scanBeacons(getNearbySubscriptionOptions(messageFilterBuilder.build()))
                },
                Response.ErrorListener { error ->
                })
        )
    }

}
