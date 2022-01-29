package com.example.mynews.ui

import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.R
import com.example.mynews.data.entities.Article
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.Status
import com.example.mynews.ui.adapters.CategoryButtonAdapter
import com.example.mynews.ui.adapters.CardviewItemAdapter

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
fun bindApiStatus(statusImageView: ImageView,
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
        Status.NO_COUNTRY -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiStatus")
fun bindApiStatus(statusTextView: TextView,
                  status: Status?) {
    when (status) {
        Status.LOADING -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.setText(R.string.updating_text)
        }
        Status.ERROR -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.setText(R.string.no_connection_text)
        }
        Status.OK -> {
            statusTextView.visibility = View.GONE
        }
        else -> {
            statusTextView.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiStatus")
fun bindApiStatus(statusButton: Button,
                  status: Status?) {
    when (status) {
        Status.LOADING -> {
            statusButton.visibility = View.GONE
        }
        Status.ERROR -> {
            statusButton.visibility = View.VISIBLE
        }
        Status.OK -> {
            statusButton.visibility = View.GONE
        }
        else -> {
            statusButton.visibility = View.GONE
        }
    }
}


@BindingAdapter("localeStatus")
fun bindLocaleStatus(statusImageView: ImageView,
                  status: Status?) {
    when (status) {
        Status.NO_COUNTRY -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_wrong_location)
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}


@BindingAdapter("localeStatus")
fun bindLocaleStatus(statusTextView: TextView,
                     status: Status?) {
    when (status) {
        Status.NO_COUNTRY -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.setText(R.string.no_country_text)
        }
        else -> {
            statusTextView.visibility = View.GONE
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

