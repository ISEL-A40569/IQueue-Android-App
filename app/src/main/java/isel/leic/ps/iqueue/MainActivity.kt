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
import isel.leic.ps.iqueue.model.UserCredentials
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onLogin(view: View) {
        val userIdViewText = findViewById<TextView>(R.id.userId).text
        val passwordViewText = findViewById<TextView>(R.id.password).text

        val userId = Integer.parseInt(userIdViewText.toString())

        application.userId = userId

        makeLoginRequest(UserCredentials(userId, passwordViewText.toString()))
    }

    fun onSignUp(view: View) {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    private fun makeLoginRequest(userCredentials: UserCredentials) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.245:8080/api/iqueue/login",
                JSONObject(application.gson.toJson(userCredentials).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())

                    application.isLoggedIn = true
                    startHomeActivity()

                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

}