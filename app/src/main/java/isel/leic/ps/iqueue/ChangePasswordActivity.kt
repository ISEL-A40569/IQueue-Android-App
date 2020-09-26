package isel.leic.ps.iqueue

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        val newUserCredentials = UserCredentials(
            application.userId!!,
            newPasswordViewText.toString()
        )

        makeChangePasswordRequest(newUserCredentials)
    }

    private fun makeChangePasswordRequest(newUserCredentials: UserCredentials) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.PUT,
                application!!.uriBuilder!!.getUserCredentialsUri(application.userId!!),
                JSONObject(application.gson.toJson(newUserCredentials).toString()),
                Response.Listener<JSONObject> { response ->
                    showConfirmationMessage()
                    application.activityStarter!!.startHomeActivity(this)
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun showConfirmationMessage() {
        val message = getString(
            R.string.new_password_confirmation
        )
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
