package isel.leic.ps.iqueue

import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import isel.leic.ps.iqueue.adapter.OperatorArrayAdapter
import isel.leic.ps.iqueue.adapter.ServiceQueueArrayAdapter
import isel.leic.ps.iqueue.model.Operator
import isel.leic.ps.iqueue.model.ServiceQueue
import org.json.JSONArray

class ServiceQueuesActivity : ListActivity() {

    private val serviceQueues: ArrayList<ServiceQueue> = ArrayList()
    private var operatorId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_queues)
        operatorId = intent.getIntExtra("operatorId", 0)
        getServiceQueues()
    }

    private fun getServiceQueues() {
        application.requestQueue.add(
            JsonArrayRequest(
                Request.Method.GET,
                "http://192.168.1.245:8080/api/iqueue/servicequeue?operatorId=${operatorId}",
                null,
                Response.Listener<JSONArray> { response ->
                    Log.d("TEST: ", response.toString())
                    var index = 0

                    while (index < response.length()) {
                        val serviceQueue: ServiceQueue =
                            application.gson.fromJson(
                                response[index].toString(),
                                ServiceQueue::class.java
                            )
                        serviceQueues.add(serviceQueue)
                        index++
                    }
                    setView()
                },
                Response.ErrorListener { error ->
                    Log.d("TEST: ", error.toString())
                })
        )
    }

    private fun setView() {
        Log.d("TEST: ", serviceQueues.toString())
        listView!!.adapter = ServiceQueueArrayAdapter(applicationContext, serviceQueues)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val intent = Intent(this, AttendanceConfirmationActivity::class.java)
        intent.putExtra("serviceQueue", serviceQueues[position])
        startActivity(intent)
    }

}
