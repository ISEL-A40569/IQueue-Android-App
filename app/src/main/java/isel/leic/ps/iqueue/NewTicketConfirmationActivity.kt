package isel.leic.ps.iqueue

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import isel.leic.ps.iqueue.model.Attendance
import org.json.JSONObject
import java.time.LocalDateTime

class NewTicketConfirmationActivity : AppCompatActivity() {

    private val ATTENDANCE_WAITING_STATUS_ID = 1
    private val ATTENDANCE_QUIT_STATUS_ID = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_ticket_confirmation)
    }

    fun onQuit(view: View) {
        makeQuitRequest(null)
    }

    fun onConfirmNewTicket(view: View) {
        makeQuitRequest(Runnable { makeAttendanceConfirmationRequest() })
    }

    private fun makeAttendanceConfirmationRequest() {
        val serviceQueueId = intent.getIntExtra("serviceQueueId", 0)

        val attendance = Attendance(
            null, serviceQueueId, null,
            application.userId!!, LocalDateTime.now().toString(),
            null, null, ATTENDANCE_WAITING_STATUS_ID
        )

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                application!!.uriBuilder!!.getAttendancesUri(),
                JSONObject(application.gson.toJson(attendance).toString()),
                Response.Listener<JSONObject> { response ->
                    attendance.attendanceId = response.getInt("attendanceId")
                    application.attendance = attendance

                    startCurrentTicketActivity()
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun startCurrentTicketActivity() {
        startActivity(Intent(this, CurrentTicketActivity::class.java))
    }

    private fun makeQuitRequest(function: Runnable?) {
        application.attendance!!.attendanceStatusId = ATTENDANCE_QUIT_STATUS_ID

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.PUT,
                application!!.uriBuilder!!.getAttendanceUri(application!!.attendance!!.attendanceId!!),
                JSONObject(application.gson.toJson(application.attendance).toString()),
                Response.Listener<JSONObject> { response ->
                    clearCurrentAttendance()

                    if (function != null) {
                        function.run()
                    } else {
                        startHomeActivity()
                    }
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun clearCurrentAttendance() {
        application.attendance = null
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
