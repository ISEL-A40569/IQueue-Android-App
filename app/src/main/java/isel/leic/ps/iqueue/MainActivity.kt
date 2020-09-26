package isel.leic.ps.iqueue

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
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

        val userId = intent.getIntExtra("userId", 0)

        if (userId != 0) {
            findViewById<TextView>(R.id.userId).text = userId.toString()
        }
    }

    fun onLogin(view: View) {
        val userIdViewText = findViewById<TextView>(R.id.userId).text
        val passwordViewText = findViewById<TextView>(R.id.password).text

        if (userIdViewText.isNullOrBlank() || passwordViewText.isNullOrBlank()) {
            showEmptyCredentialsMessage()
        } else {
            val userId = Integer.parseInt(userIdViewText.toString())

            application.userId = userId

            makeLoginRequest(UserCredentials(userId, passwordViewText.toString()))
        }
    }


    fun onSignUp(view: View) {
        application.activityStarter!!.startSignInActivity(this)
    }

    private fun makeLoginRequest(userCredentials: UserCredentials) {
        application!!.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                application.uriBuilder!!.getLoginUri(),
                JSONObject(application.gson.toJson(userCredentials).toString()),
                Response.Listener<JSONObject> { response ->
                    application.activityStarter!!.startHomeActivity(this)
                },
                Response.ErrorListener { error ->
                    showLoginErrorMessage()
                })
        )
    }

    private fun showEmptyCredentialsMessage() {
        Toast.makeText(this, R.string.empty_credentials_message, Toast.LENGTH_LONG).show()
    }

    private fun showLoginErrorMessage() {
        Toast.makeText(this, R.string.login_error_message, Toast.LENGTH_LONG).show()
    }

}