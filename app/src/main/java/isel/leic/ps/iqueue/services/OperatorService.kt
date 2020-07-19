//package isel.leic.ps.iqueue.services
//
//import android.app.IntentService
//import android.content.Intent
//import android.os.Bundle
//import android.os.ResultReceiver
//import android.util.Log
//import isel.leic.ps.iqueue.gson
//import isel.leic.ps.iqueue.model.Operator
//import isel.leic.ps.iqueue.requestQueue
//import isel.leic.ps.iqueue.requests.Callback
//import isel.leic.ps.iqueue.requests.GetOperatorRequest
//
//class OperatorService() : IntentService("OperatorService") {
//    override fun onHandleIntent(intent: Intent?) {
//
//        val receiver: ResultReceiver = intent!!.getParcelableExtra("receiver")
//
//        application.requestQueue.add(GetOperatorRequest(application.gson,
//            getOperatorsResponseCallback(receiver)))
//    }
//
//    private fun getOperatorsResponseCallback(receiver: ResultReceiver): Callback<ArrayList<Operator>> {
//        return object : Callback<ArrayList<Operator>> {
//            override fun onSuccess(response: ArrayList<Operator>) {
//                sendInfo(receiver, "operators", response!!)
//            }
//        }
//    }
//
//    private fun sendInfo(receiver: ResultReceiver, key: String, operators: ArrayList<Operator>) {
//        val bundle = Bundle()
////        bundle.putParcelable(key, operators)
//        receiver.send(200, bundle)
//    }
//}