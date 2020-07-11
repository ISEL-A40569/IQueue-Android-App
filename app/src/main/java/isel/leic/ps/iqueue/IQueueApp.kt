package isel.leic.ps.iqueue

import android.app.Application;
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class IQueueApp : Application() {

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    val gson = Gson()

}

val Application.requestQueue: RequestQueue
    get() = (this as IQueueApp).requestQueue

val Application.gson: Gson
    get() = (this as IQueueApp).gson

var userId: Integer? = null
