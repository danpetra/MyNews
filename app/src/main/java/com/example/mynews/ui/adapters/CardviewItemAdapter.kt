package com.example.mynews.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.R
import com.example.mynews.data.entities.Article
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.databinding.CardviewItemBinding

class CardviewItemAdapter(val articleListener: OnArticleListener): ListAdapter<ArticleData, CardviewItemAdapter.ArticleDataViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleDataViewHolder {
        return ArticleDataViewHolder(CardviewItemBinding.inflate(LayoutInflater.from(parent.context)), articleListener)
    }

    override fun onBindViewHolder(holder: ArticleDataViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<ArticleData>(){
        override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
            return oldItem == newItem
        }
    }

    class ArticleDataViewHolder (private var binding: CardviewItemBinding, val onArticleListener: OnArticleListener):
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(article: ArticleData){
            binding.article = article
            binding.root.setOnClickListener(this)

            val bookmarkButton = binding.root.findViewById<ImageButton>(R.id.button_bookmark)
            bookmarkButton.setOnClickListener{
                onArticleListener.onArticleBookmarkClick(adapterPosition)
                if (bookmarkButton.tag == "1") {bookmarkButton.setImageResource(R.drawable.ic_bookmark_fill_gold); bookmarkButton.tag = "2"}
                else {bookmarkButton.setImageResource(R.drawable.ic_bookmark_border_gold); bookmarkButton.tag = "1"}
            }
            val shareButton = binding.root.findViewById<ImageButton>(R.id.button_share)
            shareButton.setOnClickListener{
                onArticleListener.onArticleShareClick(adapterPosition)
            }
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            if (p0 != null)
                if(p0.id != R.id.button_bookmark && p0.id != R.id.button_share)
                    onArticleListener.onArticleClick(adapterPosition)
        }

    }

    interface OnArticleListener{
        fun onArticleClick(position: Int)
        fun onArticleShareClick(position: Int)
        fun onArticleBookmarkClick(position: Int)
    }

}
class ArticleListener(val clickListener: (article: Article) -> Unit) {
    fun onClick(article: Article) = clickListener(article)
}


