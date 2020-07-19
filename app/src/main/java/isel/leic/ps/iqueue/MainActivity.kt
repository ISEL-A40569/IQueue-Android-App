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
//    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onLogin(view: View) {
        val userIdViewText = findViewById<TextView>(R.id.userId).text
        val passwordViewText = findViewById<TextView>(R.id.password).text

        userId = Integer(Integer.parseInt(userIdViewText.toString()))

        val userCredentials = UserCredentials(userId!!, passwordViewText.toString())

        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.245:8080/api/iqueue/login",
                JSONObject(application.gson.toJson(userCredentials).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                    startActivity(Intent(this, HomeActivity::class.java))

                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    fun onSignUp(view: View) {
//        startActivity(Intent(this, SignInActivity::class.java))
        startActivity(Intent(this, OperatorsActivity::class.java))

    }
}