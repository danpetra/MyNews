package com.example.mynews.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.R
import com.example.mynews.data.entities.SourceData
import com.example.mynews.databinding.CategoryButtonItemBinding
import com.example.mynews.databinding.SourceCheckboxItemBinding


class SourcesListAdapter(val context: Context, val list: List<String>, val clickListener: (p0: Int) -> Unit ): BaseAdapter(){
    override fun getCount(): Int = list.size

    override fun getItem(p0: Int): Any = list.get(p0)

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val v = inflate(context, R.layout.source_checkbox_item, null)
        val sourceName = v.findViewById<TextView>(R.id.source_name)
        sourceName.setText(list.get(p0))
        sourceName.setOnClickListener {
            clickListener(p0)
        }
        return v
    }

}
