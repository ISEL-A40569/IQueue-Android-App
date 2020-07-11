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
import isel.leic.ps.iqueue.model.UserCredentials
import org.json.JSONObject

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
    }

    fun onChangePassword(view: View) {
        val newPasswordViewText = findViewById<TextView>(R.id.newPassword).text

        val newUserCredentials = UserCredentials(userId!!,
            newPasswordViewText.toString())

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.PUT,
                "http://192.168.1.245:8080/api/iqueue/user/${userId!!}/credentials",
                JSONObject(application.gson.toJson(newUserCredentials).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }
}
