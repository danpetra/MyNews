package com.example.mynews.ui

import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.R
import com.example.mynews.data.entities.Article
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.Status
import com.example.news.overview.CategoryButtonAdapter
import com.example.news.overview.adapters.CardviewItemAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            /*.apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))*/
            .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerViewArticle(recyclerView: RecyclerView, articles: List<ArticleData>?){
    val adapter = recyclerView.adapter as CardviewItemAdapter
    adapter.submitList(articles)
}

@BindingAdapter("list")
fun bindRecyclerViewButton(recyclerView: RecyclerView, categories: List<String>?){
    val adapter = recyclerView.adapter as CategoryButtonAdapter
    adapter.submitList(categories)
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView,
               status: Status?) {
    when (status) {
        Status.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        Status.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_no_connection)
        }
        Status.OK -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("bookmarkImage")
fun bindBookmark(button: ImageButton, isBookmarked: Boolean){
   if(isBookmarked) {button.setImageResource(R.drawable.ic_bookmark_fill); button.setTag("2")}
    else {button.setImageResource(R.drawable.ic_bookmark_border); button.setTag("1")}
}

/*
@BindingAdapter("menuBookmarkIcon")
fun bindBookmarkMenu(button: MenuItem, isBookmarked: Boolean){
    if(isBookmarked) {button.setIcon(R.drawable.ic_bookmark_fill_black)}
    else {button.setIcon(R.drawable.ic_bookmark_border_black)}
}
*/

