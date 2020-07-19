package isel.leic.ps.iqueue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import isel.leic.ps.iqueue.R
import isel.leic.ps.iqueue.model.Operator
import isel.leic.ps.iqueue.model.ServiceQueue

class ServiceQueueArrayAdapter (context: Context, val serviceQueues: ArrayList<ServiceQueue>) :
    ArrayAdapter<ServiceQueue>(context, 0, serviceQueues) {

    //    val inflater = LayoutInflater.from(context)
    val inflater = LayoutInflater.from(getContext())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val vh: ViewHolder
        if (convertView == null) {
            convertView = inflater!!.inflate(R.layout.service_queue_list_entry, parent, false)
            vh = ViewHolder(convertView!!)
            convertView.tag = vh
        } else {
            vh = convertView.tag as ViewHolder
        }

        val serviceQueue: ServiceQueue? = getItem(position)
        vh.textView!!.text = serviceQueue!!.serviceQueueDescription

        return convertView
    }

    internal class ViewHolder constructor(rootView: View) {

        var textView: TextView?

        init {
            textView = rootView.findViewById(R.id.item_date_view) as TextView?
        }
    }
}