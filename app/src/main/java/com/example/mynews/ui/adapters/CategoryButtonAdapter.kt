package com.example.mynews.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import com.example.mynews.databinding.CategoryButtonItemBinding


class CategoryButtonAdapter(val buttonListener: OnCategoryButtonListener, val clickListener:ButtonListener): ListAdapter<String, CategoryButtonAdapter.ButtonCategoryViewHolder>(DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonCategoryViewHolder {
        return ButtonCategoryViewHolder(CategoryButtonItemBinding.inflate(LayoutInflater.from(parent.context)), buttonListener)
    }

    override fun onBindViewHolder(holder: ButtonCategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, clickListener)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    class ButtonCategoryViewHolder(val binding: CategoryButtonItemBinding, val onButtonListener: OnCategoryButtonListener):
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        fun bind(category: String, clickListener: ButtonListener){
            binding.category = category
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            onButtonListener.onButtonClick(adapterPosition)
        }
    }

    interface OnCategoryButtonListener{
        fun onButtonClick(position: Int)
    }

}


class ButtonListener(val clickListener: (category: String)->Unit){
    fun onClick(category: String) = clickListener(category)
}
