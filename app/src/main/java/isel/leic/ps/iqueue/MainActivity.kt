package isel.leic.ps.iqueue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import isel.leic.ps.iqueue.model.User
import isel.leic.ps.iqueue.model.UserCredentials
import org.json.JSONObject
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onLogin(view: View) {
        val userId = findViewById<TextView>(R.id.userId).text
        val password = findViewById<TextView>(R.id.password).text

        val gson = Gson()

        val userCredentials = UserCredentials(Integer(Integer.parseInt(userId.toString())),
            password.toString())

        requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.245:8080/api/iqueue/login",
                JSONObject(gson.toJson(userCredentials).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    fun onSignUp(view: View) {
        startActivity(Intent(this, SignInActivity::class.java))
    }
}