package isel.leic.ps.iqueue

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import isel.leic.ps.iqueue.adapter.ServiceQueueArrayAdapter
import isel.leic.ps.iqueue.model.ServiceQueue
import org.json.JSONArray

class ServiceQueuesActivity : ListActivity() {

    private val serviceQueues: ArrayList<ServiceQueue> = ArrayList()

    private val SERVICEQUEUE_WITH_ANTECIPATION_TYPE_ID = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_queues)

        getServiceQueues()
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val intent = Intent(this, AttendanceConfirmationActivity::class.java)
        intent.putExtra("serviceQueue", serviceQueues[position])
        startActivity(intent)
    }

    fun onQuit(view: View) {
        startHomeActivity()
    }

    private fun getServiceQueues() {
        val operatorId: Int = intent.getIntExtra("operatorId", 0)
        makeServiceQueuesRequest(operatorId)
    }

    private fun makeServiceQueuesRequest(operatorId: Int) {
        application.requestQueue.add(
            JsonArrayRequest(
                Request.Method.GET,
                application!!.uriBuilder!!.getOperatorServiceQueuesUri(operatorId),
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

                        if (application.isOnBeaconReach) {
                            serviceQueues.add(serviceQueue)
                        } else if (serviceQueue.serviceQueueTypeId == SERVICEQUEUE_WITH_ANTECIPATION_TYPE_ID) {
                            serviceQueues.add(serviceQueue)
                        }

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

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

}
