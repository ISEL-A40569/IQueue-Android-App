package isel.leic.ps.iqueue

import android.app.Application;
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class IQueueApp : Application() {

    val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }


}

val Application.requestQueue: RequestQueue
    get() = (this as IQueueApp).requestQueue