package isel.leic.ps.iqueue

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import isel.leic.ps.iqueue.model.User
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {
//    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    fun onSignUp(view: View) {
        val userName = findViewById<EditText>(R.id.userName).text
        val email = findViewById<EditText>(R.id.userEmail).text
        val telephoneNumber = findViewById<EditText>(R.id.userTelephoneNumber).text
        val address = findViewById<EditText>(R.id.userAddress).text

        val user = User(null, userName.toString(), email.toString(),
            telephoneNumber.toString(), address.toString(), 4)

        makeSignInRequest(user)
    }

    private fun makeSignInRequest(user: User) {
        application.requestQueue.add(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.245:8080/api/iqueue/user",
                JSONObject(application.gson.toJson(user).toString()),
                Response.Listener<JSONObject> { response ->
                    Log.d("TEST: ", response.toString())
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }
}
