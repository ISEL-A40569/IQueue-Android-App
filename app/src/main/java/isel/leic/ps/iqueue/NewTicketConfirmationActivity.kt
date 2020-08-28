package isel.leic.ps.iqueue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import isel.leic.ps.iqueue.model.Attendance
import org.json.JSONObject
import java.time.LocalDateTime
import kotlin.reflect.KFunction

class NewTicketConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_ticket_confirmation)
    }

    fun onQuit(view: View) {
        makeQuitRequest(null)
    }

    fun onConfirmNewTicket(view: View) {
        makeQuitRequest( Runnable { makeAttendanceConfirmationRequest() } )
    }

    private fun makeAttendanceConfirmationRequest() {
        val serviceQueueId = intent.getIntExtra("serviceQueueId", 0)

        val attendance = Attendance(
            null, serviceQueueId, null,
            application.userId!!, LocalDateTime.now().toString(),
            null, null, 1
        )

        Log.d("TEST: ", JSONObject(application.gson.toJson(attendance).toString()).toString())

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.245:8080/api/iqueue/attendance",
                JSONObject(application.gson.toJson(attendance).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

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
        application.attendance!!.attendanceStatusId = 4

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.PUT,
                "http://192.168.1.245:8080/api/iqueue/attendance/${application.attendance!!.attendanceId}",
                JSONObject(application.gson.toJson(application.attendance).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

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
