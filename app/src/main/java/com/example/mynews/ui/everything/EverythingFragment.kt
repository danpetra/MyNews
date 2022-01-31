package com.example.mynews.ui.everything

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mynews.R
import com.example.mynews.databinding.FragmentEverythingBinding
import com.example.mynews.databinding.SourceMenuBinding
import com.example.mynews.ui.adapters.CardviewItemAdapter
import com.example.mynews.ui.adapters.SourcesListAdapter
import com.example.mynews.ui.top.TopFragmentDirections
import com.example.mynews.ui.top.TopViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class EverythingFragment: Fragment(), DIAware, CardviewItemAdapter.OnArticleListener {

    override val di by closestDI()
    private val viewModelFactory: EverythingViewModelFactory by instance()

    private lateinit var viewModel: EverythingViewModel
    private var _binding: FragmentEverythingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory).get(EverythingViewModel::class.java)
        _binding = FragmentEverythingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        binding.articlesListE.adapter = CardviewItemAdapter(this)

        val enterText = binding.root.findViewById<TextView>(R.id.enter_text)
        viewModel.showEnterText.observe(viewLifecycleOwner){
            if (it) enterText.visibility = View.VISIBLE
            else enterText.visibility = View.GONE
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**  override Clickers  **/

    override fun onArticleClick(position: Int) {
        val article  = binding.viewModel?.articles?.value?.get(position)
        if (article != null) {
            view?.findNavController()?.navigate(
                EverythingFragmentDirections.actionNavEverythingToArticleFragment(
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
        val article  = binding.viewModel?.articles?.value?.get(position)
        if (article != null) {
            article.isBookmarked = article.isBookmarked != true
            viewModel.onBookmarkEvent(article)
        }
    }

    /**  override Menu  **/

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.action_share)
        menu.removeItem(R.id.action_bookmark)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.action_search)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = "Search news.."

        viewModel.query?.let{ searchView.setQuery(it, true) }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let{
                    if(p0.length>2) {
                        viewModel.query = p0
                        viewModel.update()
                    }
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let{
                    viewModel.query = p0
                    viewModel.update()
                }
                return false
            }
        })
        searchMenuItem.icon.setVisible(true,true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.sources_menu_item) showSourceMenu()
        return super.onOptionsItemSelected(item)
    }


    private fun shareArticle(message: String) {
        startActivity(viewModel.getShareIntent(message))
    }

    fun showSourceMenu() {
        val dialog = AlertDialog.Builder(this.requireContext())
        dialog.setTitle("Choose source")
        val inflater = LayoutInflater.from(this.requireContext())
        val binding = SourceMenuBinding.inflate(inflater)
        val sourceList = binding.root.findViewById<ListView>(R.id.source_list)
        val sourcesList = viewModel.sources
        val sourcesNameList = sourcesList?.map { it.name }
        var sourceId:String? = null
        var sourceName:String? = null
        val adapter = context?.let {context->
            sourcesNameList?.let{list->
                SourcesListAdapter(context, list){
                        p0->Toast.makeText(context,"Chosed ${sourcesList.get(p0).id}",Toast.LENGTH_LONG).show()
                    sourceId = sourcesList.get(p0).id
                    sourceName = sourcesList.get(p0).name
                }
            }?: run { SourcesListAdapter(context, listOf("No items")){} }
        }
        sourceList.adapter = adapter

        dialog.setView(binding.root)

        dialog.setNegativeButton("Cancel"){dialogInterface,_ -> dialogInterface.dismiss()}
        dialog.setPositiveButton("Set"){dialogInterface,_ ->
            viewModel.currentSource = sourceId
            viewModel.update()
            dialogInterface.dismiss()
            view?.let {
                Snackbar.make(it, "You are watching the news from $sourceName", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Return", View.OnClickListener {
                        viewModel.currentSource = null
                        viewModel.update()
                    })

                    .show()
            }
        }
        dialog.show()
    }

}