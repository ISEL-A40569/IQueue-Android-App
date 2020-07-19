package isel.leic.ps.iqueue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import isel.leic.ps.iqueue.R
import isel.leic.ps.iqueue.model.Operator

class OperatorArrayAdapter (context: Context, val operators: ArrayList<Operator>) : ArrayAdapter<Operator>(context, 0, operators) {

//    val inflater = LayoutInflater.from(context)
    val inflater = LayoutInflater.from(getContext())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val vh: ViewHolder
        if (convertView == null) {
            convertView = inflater!!.inflate(R.layout.operator_list_entry, parent, false)
            vh = ViewHolder(convertView!!)
            convertView.tag = vh
        } else {
            vh = convertView.tag as ViewHolder
        }

        val operator: Operator? = getItem(position)
        vh.textView!!.text = operator!!.operatorDescription

        return convertView
    }

    internal class ViewHolder constructor(rootView: View) {

        var textView: TextView?

        init {
            textView = rootView.findViewById(R.id.item_date_view) as TextView?
        }
    }
}