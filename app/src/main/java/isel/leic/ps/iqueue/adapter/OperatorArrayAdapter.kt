package isel.leic.ps.iqueue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import isel.leic.ps.iqueue.R
import isel.leic.ps.iqueue.model.Operator

class OperatorArrayAdapter(context: Context, operators: ArrayList<Operator>) :
    ArrayAdapter<Operator>(context, 0, operators) {

    private val inflater = LayoutInflater.from(getContext())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var localConvertView = convertView
        val vh: ViewHolder
        if (localConvertView == null) {
            localConvertView = inflater!!.inflate(R.layout.operator_list_entry, parent, false)
            vh = ViewHolder(localConvertView!!)
            localConvertView.tag = vh
        } else {
            vh = localConvertView.tag as ViewHolder
        }

        val operator: Operator? = getItem(position)
        vh.textView!!.text = operator!!.operatorDescription

        return localConvertView
    }

    internal class ViewHolder constructor(rootView: View) {
        var textView: TextView? = rootView.findViewById(R.id.item_date_view) as TextView?
    }
}