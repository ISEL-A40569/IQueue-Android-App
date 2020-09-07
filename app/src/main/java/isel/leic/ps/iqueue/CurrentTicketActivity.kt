package isel.leic.ps.iqueue

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import kotlin.concurrent.thread


class CurrentTicketActivity : AppCompatActivity() {

    private var currentTicket: Int? = null

    private var ownTicket: Int? = null

    @Volatile
    private var okToRefreshCurrentTicket: Boolean = true

    @Volatile
    private var attendanceStatus = 0

    @Volatile
    private var ticketsLeftMessageIsSent: Boolean = false

    private val ATTENDANCE_DONE_STATUS_ID = 3
    private val ATTENDANCE_QUIT_STATUS_ID = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_ticket)

        getAttendanceTicket()
        okToRefreshCurrentTicket = true

        startRefreshCurrentTicketThread()
    }

    fun onQuit(view: View) {
        makeQuitRequest()
    }

    private fun makeQuitRequest() {
        application.attendance!!.attendanceStatusId = ATTENDANCE_QUIT_STATUS_ID

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.PUT,
                application!!.uriBuilder!!.getAttendanceUri(application.attendance!!.attendanceId!!),
                JSONObject(application.gson.toJson(application.attendance).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    clearCurrentAttendance()
                    okToRefreshCurrentTicket = false

                    application.activityStarter!!.startHomeActivity(applicationContext)
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun getAttendanceTicket() {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.GET,
                 application!!.uriBuilder!!.getAttendanceTicketUri(application.attendance!!.attendanceId!!),
                null,
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    ownTicket = response.getInt("ticketNumber")

                    findViewById<TextView>(R.id.ticketNumberView).text =
                        ownTicket.toString()

                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun getCurrentTicket() {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.GET,
                application!!.uriBuilder!!.getCurrentAttendanceUri(application.attendance!!.serviceQueueId),
                null,
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    currentTicket = response.getInt("currentAttendanceTicketNumber")

                    if (currentTicket != null && ownTicket != null && ownTicket!! - currentTicket!! == application.ticketsLeftWarningLimit
                        && !ticketsLeftMessageIsSent
                    ) {

                        ticketsLeftMessageIsSent = true
                        sendNotification(
                            getString(
                                R.string.tickets_left_message,
                                application.ticketsLeftWarningLimit
                            )
                        )
                    }

                    if (currentTicket == ownTicket) {
                        if (application.isOnBeaconReach) {
                            getAttendance(application.attendance!!.attendanceId!!)
                        } else {
                            ownTicket = null
                            okToRefreshCurrentTicket = false
                            application.activityStarter!!
                                .startNewTicketConfirmationActivity(this, application.attendance!!.serviceQueueId)
                        }
                    }

                    findViewById<TextView>(R.id.currentTicketView).text =
                        currentTicket.toString()
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun startRefreshCurrentTicketThread() {
        thread {
            while (okToRefreshCurrentTicket) {
                getCurrentTicket()
                Thread.sleep(1000)
            }
        }
    }

    private fun startCheckStatusThread() {
        thread {
            while (attendanceStatus != ATTENDANCE_DONE_STATUS_ID) {
                getAttendanceStatus(application.attendance!!.attendanceId!!)
                Thread.sleep(1000)
            }
            application.activityStarter!!.startAttendanceClassificationActivity(this)
        }
    }

    private fun getAttendanceStatus(attendanceId: Int) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.GET,
                application!!.uriBuilder!!.getAttendanceUri(attendanceId),
                null,
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    attendanceStatus = response.getInt("attendanceStatusId")
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun getAttendance(attendanceId: Int) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.GET,
                application!!.uriBuilder!!.getAttendanceUri(attendanceId),
                null,
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                    val deskId = response.getInt("deskId")
                    makeDeskRequest(deskId)
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun sendNotification(text: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "notificationChannel"

        val notificationChannel = NotificationChannel(
            channelId,
            "notificationChannel", NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationManager.createNotificationChannel(notificationChannel)

        val notifyID = 1

        val notifyBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("IQueue")
            .setContentText(text)
            .setSmallIcon(R.drawable.notification_template_icon_bg)
            .setVibrate(longArrayOf(1000, 1000))

        notificationManager.notify(
            notifyID,
            notifyBuilder.build()
        )
    }

    private fun clearCurrentAttendance() {
        application.attendance = null
    }

    private fun makeDeskRequest(deskId: Int) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.GET,
                application!!.uriBuilder!!.getDeskUri(deskId),
                null,
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    val deskDescription = response.getString("deskDescription")

                    okToRefreshCurrentTicket = false
                    sendNotification(getString(R.string.call_ticket_message, deskDescription))
                    startCheckStatusThread()

                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

}
