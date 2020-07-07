package isel.leic.ps.iqueue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onLogin(view: View) {
        val userId = findViewById<TextView>(R.id.userId).text
        val password = findViewById<TextView>(R.id.password).text

        val credentials = StringBuilder()
            .append(userId)
            .append(" ")
            .append(password)
            .toString()

        Toast.makeText(this, credentials, Toast.LENGTH_LONG).show()
    }

    fun onSignUp(view: View) {
        startActivity(Intent(this, SignInActivity::class.java))
    }
}