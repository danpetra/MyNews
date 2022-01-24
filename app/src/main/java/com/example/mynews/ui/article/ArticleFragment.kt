package com.example.mynews.ui.article

import android.app.SearchManager
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebView
import android.widget.SearchView
import com.example.mynews.R
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.Source
import com.example.mynews.data.network.MyWebViewClient
import com.example.mynews.databinding.FragmentArticleBinding
import com.example.mynews.ui.top.TopViewModelFactory
import com.example.news.article.ArticleViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class ArticleFragment : Fragment(), DIAware {

    override val di by closestDI()
    private val viewModelFactory: ArticleViewModelFactory by instance()


    private lateinit var viewModel: ArticleViewModel
    lateinit var args: ArticleFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentArticleBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        args = ArticleFragmentArgs.fromBundle(requireArguments())

        val myWebViewClient = MyWebViewClient()
        binding.root.let{
            val webView: WebView = binding.root.findViewById(R.id.article_page)
            webView.webViewClient = myWebViewClient
            webView.loadUrl(args.url)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ArticleViewModel::class.java)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.action_search)
        val bookmarkItem = menu.findItem(R.id.action_bookmark)
        if (args.isBookmarked) bookmarkItem.setIcon(R.drawable.ic_bookmark_fill_black)
        else bookmarkItem.setIcon(R.drawable.ic_bookmark_border_black)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_share -> shareArticle(args.url)
            R.id.action_bookmark -> {bookmarkArticle(
                ArticleData(
                    Source(args.sourceId,args.sourceId),
                    args.author,
                    args.title,
                    args.description,
                    args.url,
                    args.urlToImage,
                    args.publishedAt,
                    args.content,
                    args.userId,
                    args.isBookmarked))
                if (args.isBookmarked) item.setIcon(R.drawable.ic_bookmark_border_black)
                else item.setIcon(R.drawable.ic_bookmark_fill_black)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareArticle(message: String) {
        startActivity(viewModel.getShareIntent(message))
    }

    private fun bookmarkArticle(article: ArticleData) {
        viewModel.onBookmarkEvent(article)
    }

}