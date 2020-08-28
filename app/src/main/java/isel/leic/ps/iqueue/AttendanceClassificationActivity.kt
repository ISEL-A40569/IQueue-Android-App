package isel.leic.ps.iqueue

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import isel.leic.ps.iqueue.model.AttendanceClassification
import org.json.JSONObject
import java.time.LocalDateTime

class AttendanceClassificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_classification)
    }

    fun onClassifyAttendance(view: View) {
        val rate = findViewById<RatingBar>(R.id.rate).rating.toInt()

        val observations = findViewById<TextView>(R.id.observations).text

        val attendanceClassification = AttendanceClassification(
            application.attendance!!.attendanceId!!,
            LocalDateTime.now().toString(), rate,
            observations.toString()
        )
        application.attendance = null

        makeAttendanceClassificationRequest(attendanceClassification)
    }

    private fun makeAttendanceClassificationRequest(attendanceClassification: AttendanceClassification) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.245:8080/api/iqueue/attendance/classification",
                JSONObject(application.gson.toJson(attendanceClassification).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    showThankYouMessage()
                    startHomeActivity()
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun showThankYouMessage() {
        Toast.makeText(this, getString(R.string.thank_you_message), Toast.LENGTH_SHORT).show()
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
