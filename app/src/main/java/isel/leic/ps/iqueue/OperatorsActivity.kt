package isel.leic.ps.iqueue

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import isel.leic.ps.iqueue.adapter.OperatorArrayAdapter
import isel.leic.ps.iqueue.model.Operator
import org.json.JSONArray

class OperatorsActivity : ListActivity() {

    private var operators: ArrayList<Operator> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators)

        getOperators()
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        Toast.makeText(this, operators[position].operatorDescription, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, ServiceQueuesActivity::class.java)
        intent.putExtra("operatorId", operators[position].operatorId)
        startActivity(intent)
    }

    private fun getOperators() {
        makeOperatorsRequest()
    }

    private fun makeOperatorsRequest() {
        application.requestQueue.add(
            JsonArrayRequest(
                Request.Method.GET,
                "http://192.168.1.245:8080/api/iqueue/operator",
                null,
                Response.Listener<JSONArray> { response ->
                    var index = 0

                    while (index < response.length()) {
                        val operator: Operator =
                            application.gson.fromJson(
                                response[index].toString(),
                                Operator::class.java
                            )
                        operators.add(operator)
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
        Log.d("TEST: ", operators.toString())
        listView!!.adapter = OperatorArrayAdapter(applicationContext, operators)
    }

}
