package isel.leic.ps.iqueue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import isel.leic.ps.iqueue.model.User
import org.json.JSONArray
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
            telephoneNumber.toString(), address.toString(), Integer(4)
        )

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
