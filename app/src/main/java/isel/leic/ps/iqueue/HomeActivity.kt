package isel.leic.ps.iqueue

import android.app.PendingIntent
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.*
import org.json.JSONObject
import kotlin.concurrent.thread


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        startScanBeaconsThread()
    }

    fun onChangePassword(view: View) {
        startChangePasswordActivity()
    }

    fun onGetOperators(view: View) {
        startOperatorsActivity()
    }

    fun onCheckTicket(view: View) {
        if (application.attendance != null) {
            startCurrentTicketActivity()
        } else {
            showNoTicketsMessage()
        }
    }

    private fun startScanBeaconsThread() {
//        thread {
//            while (application.isLoggedIn) {
                scanBeacons()
//            }
//        }
    }

    private fun scanBeacons() {
        Log.d("TEST: ", "On scanBeacons")
        val messagesClient = Nearby.getMessagesClient(this)

        val messageListener = createMessageListener()

        val subscribeOptions = getNearbySubscriptionOptions()

        messagesClient.subscribe(messageListener, subscribeOptions)

//        Thread.sleep(1000)
//
//        messagesClient.unsubscribe(messageListener)
    }

    private fun createMessageListener(): MessageListener {
        return object: MessageListener() {
            override fun onFound(message: Message?) {
                super.onFound(message)
                val eddystoneUid =
                    isel.leic.ps.iqueue.model.EddystoneUid(byteArrayToHex(message!!.content))
//                Log.d("TEST: ", application.gson.toJson(eddystoneUid).toString())

                Log.d("TEST: ", "On Found Message")
                application.isOnBeaconReach = true
//                makeBeaconEddystoneUidRequest(eddystoneUid)
            }

            override fun onLost(message: Message?) {
                Log.d("TEST: ", "On Lost Message")
                application.isOnBeaconReach = false
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
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                application!!.uriBuilder!!.getBeaconEddystoneUidUri(),
                JSONObject(application.gson.toJson(eddystoneUid).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    if (application.attendance == null)
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

    private fun startChangePasswordActivity() {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }

    private fun startOperatorsActivity() {
        startActivity(Intent(this, OperatorsActivity::class.java))
    }

    private fun startCurrentTicketActivity() {
        startActivity(Intent(this, CurrentTicketActivity::class.java))
    }

    private fun showNoTicketsMessage() {
        Toast.makeText(this, getString(R.string.no_tickets_message), Toast.LENGTH_SHORT)
            .show()
    }


}
