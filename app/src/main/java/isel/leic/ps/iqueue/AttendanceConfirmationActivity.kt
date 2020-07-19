package isel.leic.ps.iqueue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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
        val attendance = Attendance(serviceQueue!!.serviceQueueId, null, 4, LocalDateTime.now().toString(),
            null, null, 1)

        Log.d("TEST: ", JSONObject(application.gson.toJson(attendance).toString()).toString())

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.245:8080/api/iqueue/attendance",
                JSONObject(application.gson.toJson(attendance).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    fun onRejectAttendance(view: View) {
        // TODO: redirect to elsewhere
    }
}
