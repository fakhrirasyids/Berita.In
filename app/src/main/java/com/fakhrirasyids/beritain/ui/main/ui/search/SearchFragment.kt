package com.fakhrirasyids.beritain.ui.main.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakhrirasyids.beritain.data.remote.response.ArticlesItem
import com.fakhrirasyids.beritain.databinding.FragmentSearchBinding
import com.fakhrirasyids.beritain.ui.adapter.NewsAdapter
import com.fakhrirasyids.beritain.ui.adapter.OnClickItemCallback
import com.fakhrirasyids.beritain.ui.main.ui.webview.WebviewActivity
import com.fakhrirasyids.beritain.utils.Constants

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private lateinit var searchViewModel: SearchViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        searchViewModel = obtainViewModel(requireActivity())

        setUpSearchView()
        observeLoading()
        observeError()

        return binding.root
    }

    private fun obtainViewModel(activity: FragmentActivity): SearchViewModel {
        return ViewModelProvider(activity)[SearchViewModel::class.java]
    }

    private fun setUpSearchView() {
        dummySearchText(true)
        with(binding) {
            svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchViewModel.getNewsSearch(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun observeLoading() {
        searchViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                dummySearchText(false)
            }
            showLoading(it)
        }
    }

    private fun observeError() {
        searchViewModel.isError.observe(viewLifecycleOwner) {
            dummySearchText(false)
            showError(it)
            if (!it) {
                observeSearchedNews()
            }
        }
    }

    private fun observeSearchedNews() {
        searchViewModel.searchedNews.observe(viewLifecycleOwner) {
            setListUserData(it)
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.tvError.visibility = View.VISIBLE
            binding.rvNews.adapter = null
        } else {
            binding.tvError.visibility = View.GONE
        }
    }

    private fun setListUserData(listNews: ArrayList<ArticlesItem>) {
        dummySearchText(false)
        val adapter = NewsAdapter(listNews)
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvNews.layoutManager = horizontalLayoutManager
        binding.rvNews.adapter = adapter
        adapter.setOnItemClickCallback(object : OnClickItemCallback {
            override fun onNewsClicked(news: ArticlesItem) {
                val iWebview = Intent(context, WebviewActivity::class.java)
                iWebview.putExtra(Constants.NEWS_URL, news.url)
                startActivity(iWebview)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun dummySearchText(isEmpty: Boolean) {
        binding.dummySearch.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}