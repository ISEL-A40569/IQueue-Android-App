package isel.leic.ps.iqueue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import isel.leic.ps.iqueue.R
import isel.leic.ps.iqueue.model.ServiceQueue

class ServiceQueueArrayAdapter(context: Context, val serviceQueues: ArrayList<ServiceQueue>) :
    ArrayAdapter<ServiceQueue>(context, 0, serviceQueues) {

    private val inflater = LayoutInflater.from(getContext())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var localConvertView = convertView
        val vh: ViewHolder
        if (localConvertView == null) {
            localConvertView = inflater!!.inflate(R.layout.service_queue_list_entry, parent, false)
            vh = ViewHolder(localConvertView!!)
            localConvertView.tag = vh
        } else {
            vh = localConvertView.tag as ViewHolder
        }

        val serviceQueue: ServiceQueue? = getItem(position)
        vh.textView!!.text = serviceQueue!!.serviceQueueDescription

        return localConvertView
    }

    internal class ViewHolder constructor(rootView: View) {
        var textView: TextView? = rootView.findViewById(R.id.item_date_view) as TextView?
    }
}