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
        thread {
            scanBeacons()
        }
    }

    private fun scanBeacons() {
        Log.d("TEST: ", "On scanBeacons")
        application!!.messagesClient.subscribe(
            application.messagesListener,
            application.subscribeOptions
        )
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
