package isel.leic.ps.iqueue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun onChangePassword(view: View) {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }
}
