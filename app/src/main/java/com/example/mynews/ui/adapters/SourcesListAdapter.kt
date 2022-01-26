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

//
//
///*class SourcesListAdapter(val list: List<SourceData>/*, val sourceListener: OnSourceListener*/):*/
//class SourcesListAdapter(/*val sourceListener: OnSourceListener*/ val clickListener: SourceListener):
//    /*ListAdapter<SourceData, SourcesListAdapter.SourcesListViewHolder>(DiffCallback){*/
//    ListAdapter<String, SourcesListAdapter.SourcesListViewHolder>(CategoryButtonAdapter.DiffCallback){
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesListViewHolder {
//
//        return SourcesListViewHolder(SourceCheckboxItemBinding.inflate(LayoutInflater.from(parent.context))/*, sourceListener*/)
//    }
//
//    override fun onBindViewHolder(holder: SourcesListViewHolder, position: Int) {
//        val source = getItem(position)
//        holder.bind(source, clickListener)
//    }
//
///*    companion object DiffCallback: DiffUtil.ItemCallback<SourceData>(){
//        override fun areItemsTheSame(oldItem: SourceData, newItem: SourceData): Boolean {
//            return oldItem === newItem
//        }
//        override fun areContentsTheSame(oldItem: SourceData, newItem: SourceData): Boolean {
//            return oldItem == newItem
//        }
//    }*/
//
//    companion object DiffCallback: DiffUtil.ItemCallback<String>(){
//        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//            return oldItem === newItem
//        }
//        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    class SourcesListViewHolder(
//        val binding: SourceCheckboxItemBinding/*, val onSourceListener: OnSourceListener*/):
//        RecyclerView.ViewHolder(binding.root)/*, View.OnClickListener*/{
//        //fun bind(source: SourceData){
//        fun bind(source: String, clickListener: SourceListener){
//            binding.source = source
//            binding.clickListener = clickListener
//            binding.executePendingBindings()
//        }
//
//     /*   override fun onClick(p0: View?) {
//            onSourceListener.onSourceClick(adapterPosition)
//        }*/
//    }
//
//  /*  interface OnSourceListener{
//        fun onSourceClick(position: Int)
//    }*/
//
//}
//
//class SourceListener(val clickListener: (category: String)->Unit){
//    fun onClick(category: String) = clickListener(category)
//}



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
