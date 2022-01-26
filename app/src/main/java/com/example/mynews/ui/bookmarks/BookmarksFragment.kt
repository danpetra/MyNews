package com.example.mynews.ui.bookmarks

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mynews.R
import com.example.mynews.databinding.FragmentBookmarksBinding
import com.example.mynews.ui.adapters.CardviewItemAdapter
import com.example.mynews.ui.top.TopFragmentDirections
import com.example.mynews.ui.top.TopViewModelFactory
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class BookmarksFragment : Fragment(), DIAware, CardviewItemAdapter.OnArticleListener {

    override val di by closestDI()
    private val viewModelFactory: BookmarksViewModelFactory by instance()

    private lateinit var viewModel: BookmarksViewModel
    private var _binding: FragmentBookmarksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookmarksViewModel::class.java)
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        val root: View = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.bArticlesList.adapter = CardviewItemAdapter(this)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.action_share)
        menu.removeItem(R.id.action_search)
        menu.removeItem(R.id.action_bookmark)
    }


    override fun onArticleClick(position: Int) {
        val article  = binding.viewModel?.articles?.value?.get(position)
        if (article != null) {
            Toast.makeText(context, "${article.title}", Toast.LENGTH_LONG).show()
            view?.findNavController()?.navigate(BookmarksFragmentDirections.actionNavBookmarksToArticleFragment(
                article.url,article.title, article.author, article.content, article.urlToImage, article.publishedAt,
                article.description, article.userId, article.isBookmarked, article.source?.id, article.source?.name))
        }
    }

    override fun onArticleShareClick(position: Int) {
        val article  = binding.viewModel?.articles?.value?.get(position)
        if (article != null) {
            shareArticle(article.url)
        }
    }

    override fun onArticleBookmarkClick(position: Int) {
        val article = binding.viewModel?.articles?.value?.get(position)
        val key = article?.url
        if (key != null) {
            viewModel.deleteBookmark(key)
            viewModel.getBookmarks()
        }
    }

    private fun shareArticle(message: String) {
        startActivity(viewModel.getShareIntent(message))
    }
}