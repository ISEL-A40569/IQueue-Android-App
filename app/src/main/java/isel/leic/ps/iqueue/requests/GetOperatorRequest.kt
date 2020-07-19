//package isel.leic.ps.iqueue.requests
//
//import android.os.ResultReceiver
//import android.util.Log
//import com.android.volley.Request
//import com.android.volley.Response
//import com.android.volley.toolbox.JsonArrayRequest
//import com.google.gson.Gson
//import isel.leic.ps.iqueue.model.Operator
//import org.json.JSONArray
//
//class GetOperatorRequest(gson: Gson, callback: Callback<ArrayList<Operator>>) : JsonArrayRequest(
//    Request.Method.GET,
//    "http://192.168.1.245:8080/api/iqueue/operator",
//    null,
//    Response.Listener<JSONArray> { response ->
//        val operators: ArrayList<Operator> = ArrayList()
//        var index = 0
//
//        while (index < response.length()) {
//            val operator: Operator =
//                gson.fromJson(
//                    response[index].toString(),
//                    Operator::class.java
//                )
//            operators.add(operator)
//            index++
//        }
//
//        callback.onSuccess(operators)
//    },
//    Response.ErrorListener { error ->
//        Log.d("TEST: ", error.toString())
//    })