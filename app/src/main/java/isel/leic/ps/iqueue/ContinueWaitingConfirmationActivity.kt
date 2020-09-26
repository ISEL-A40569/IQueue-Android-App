package isel.leic.ps.iqueue

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ContinueWaitingConfirmationActivity : AppCompatActivity() {

    private val ATTENDANCE_QUIT_STATUS_ID = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue_waiting_confirmation)
    }

    fun onContinueWaiting(view: View) {
        application.activityStarter!!.startCurrentTicketActivity(this)
    }

    fun onQuitWaiting(view: View) {
        makeQuitRequest()
        application.activityStarter!!.startHomeActivity(this)
    }

    private fun makeQuitRequest() {
        application.attendance!!.attendanceStatusId = ATTENDANCE_QUIT_STATUS_ID

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.PUT,
                application!!.uriBuilder!!.getAttendanceUri(application!!.attendance!!.attendanceId!!),
                JSONObject(application.gson.toJson(application.attendance).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                    clearCurrentAttendance()
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun clearCurrentAttendance() {
        application.attendance = null
    }
}
