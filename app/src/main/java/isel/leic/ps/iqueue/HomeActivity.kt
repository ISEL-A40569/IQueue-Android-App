package isel.leic.ps.iqueue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        onSubscribe()
    }

    fun onChangePassword(view: View) {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }

    fun onSubscribe() {
        val options = SubscribeOptions.Builder()
            .setStrategy(Strategy.BLE_ONLY)
            .setFilter(MessageFilter.Builder()
                .includeEddystoneUids("00112233445566778899", "abcde0eb00bd")
                .build())
            .build()

        val mMessageListener = object : MessageListener() {
            override fun onFound(message: Message?) {
                Log.d("TEST", "Found message: " + String(message!!.content))
            }

            override fun onLost(message: Message?) {
                Log.d("TEST", "Lost sight of message: " + String(message!!.content))
            }
        }

        Nearby.getMessagesClient(this).subscribe(mMessageListener, options)
    }
}
