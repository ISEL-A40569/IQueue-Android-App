package isel.leic.ps.iqueue

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import isel.leic.ps.iqueue.model.Attendance
import isel.leic.ps.iqueue.model.ServiceQueue
import org.json.JSONObject
import java.time.LocalDateTime

class AttendanceConfirmationActivity : AppCompatActivity() {

    private var serviceQueue: ServiceQueue? = null

    private var waitingCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_confirmation)
        serviceQueue = intent.getParcelableExtra("serviceQueue")

        val text = getString(R.string.confirm_attendance, serviceQueue!!.serviceQueueDescription)
        findViewById<TextView>(R.id.confirmation_message).text = text
    }

    fun onConfirmAttendance(view: View) {
        makeGetWaitingCountRequest()
    }

    fun onRejectAttendance(view: View) {
        application.activityStarter!!.startHomeActivity(this)
    }

    private fun makeAttendanceConfirmationRequest(attendance: Attendance) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                application!!.uriBuilder!!.getAttendancesUri(),
                JSONObject(application.gson.toJson(attendance).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    attendance.attendanceId = response.getInt("attendanceId")
                    application.attendance = attendance

                    application.activityStarter!!.startCurrentTicketActivity(applicationContext)
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun makeGetWaitingCountRequest() {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.GET,
                application!!.uriBuilder!!.getServiceQueueWaitingCountUri(serviceQueue!!.serviceQueueId),
                null,
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                    waitingCount = response.getInt("waitingCount")

                    // before confirm attendance, we must check if service queue attendance limit allows it
                    if (serviceQueue!!.dailyLimit > 0 && waitingCount == serviceQueue!!.dailyLimit) {
                        showOverDailyLimitMessage()
                        application!!.activityStarter!!.startHomeActivity(this)
                    } else {
                        val attendance = Attendance(
                            null, serviceQueue!!.serviceQueueId, null,
                            application.userId!!, LocalDateTime.now().toString(),
                            null, null, 1
                        )

                        makeAttendanceConfirmationRequest(attendance)
                    }

                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun showOverDailyLimitMessage() {
        val message = getString(
            R.string.over_daily_limit_message
        )
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
