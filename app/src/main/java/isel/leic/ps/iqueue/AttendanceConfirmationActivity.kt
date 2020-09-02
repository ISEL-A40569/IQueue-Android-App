package isel.leic.ps.iqueue

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_confirmation)
        serviceQueue = intent.getParcelableExtra("serviceQueue")

        val text = getString(R.string.confirm_attendance, serviceQueue!!.serviceQueueDescription)
        findViewById<TextView>(R.id.confirmation_message).text = text
    }

    fun onConfirmAttendance(view: View) {
        val attendance = Attendance(
            null, serviceQueue!!.serviceQueueId, null,
            application.userId!!, LocalDateTime.now().toString(),
            null, null, 1
        )

        Log.d("TEST: ", JSONObject(application.gson.toJson(attendance).toString()).toString())

        // TODO: before confirm attendance, we must check if service queue attendance limit allows it

        makeAttendanceConfirmationRequest(attendance)
    }

    fun onRejectAttendance(view: View) {
        startOperatorsActivity()
    }

    private fun makeAttendanceConfirmationRequest(attendance: Attendance) {
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

    private fun startOperatorsActivity() {
        startActivity(Intent(this, OperatorsActivity::class.java))
    }
}
