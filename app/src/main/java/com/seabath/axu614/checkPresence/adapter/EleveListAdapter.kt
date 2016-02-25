package com.seabath.axu614.checkPresence.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.seabath.axu614.checkPresence.R
import com.seabath.axu614.checkPresence.classe.EleveClass
import java.util.*

class EleveListAdapter(context: Context, tabNav: ArrayList<EleveClass>) : BaseAdapter() {

    internal var sList : ArrayList<EleveClass>
    private val mInflator: LayoutInflater

    init {
        this.mInflator = LayoutInflater.from(context)
        this.sList = tabNav
    }

    override fun getCount(): Int {
        return sList.size
    }

    override fun getItem(position: Int): EleveClass {
        return sList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ListRowHolder

        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.unicorn_item, parent, false)
            vh = ListRowHolder(view)
            view!!.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        vh.firstLine.text = sList[position].name
        vh.secondLine.text = sList[position].location
        return view
    }

    private class ListRowHolder(row: View?) {
        val firstLine: TextView
        val secondLine: TextView
        val image: ImageView

        init {
            this.firstLine = row?.findViewById(R.id.firstLine_unicorn) as TextView
            this.secondLine = row?.findViewById(R.id.secondLine_unicorn) as TextView
            this.image= row?.findViewById(R.id.icon_unicorn) as ImageView
        }
    }

}