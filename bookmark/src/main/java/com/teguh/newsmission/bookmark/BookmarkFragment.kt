package com.teguh.newsmission.bookmark

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.teguh.newsmission.bookmark.databinding.FragmentBookmarkBinding
import com.teguh.setiawan.newsmission.R
import com.teguh.setiawan.newsmission.ui.home.HomeAdapter
import com.teguh.setiawan.newsmission.ui.utils.Common.navigateToDetail
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class BookmarkFragment : Fragment(com.teguh.newsmission.bookmark.R.layout.fragment_bookmark) {
    private val binding by viewBinding<FragmentBookmarkBinding>()
    private val viewModel: BookmarkViewModel by viewModel()
    private var newsAdapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(bookmarkModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.black)

        newsAdapter = HomeAdapter{
            navigateToDetail(it)
        }

        binding.rvFavoriteCharacters.apply {
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        viewModel.news.observe(viewLifecycleOwner) {
            newsAdapter?.submitList(it)
            binding.layoutError.apply {
                if (it.isNotEmpty()) {
                    showError(true, "")
                } else {
                    showError(false, getString(com.teguh.newsmission.bookmark.R.string.tvEmptyBookmark))
                }
            }
        }
    }

    private fun showError(isVisible: Boolean, message: String) {
        binding.layoutError.apply {
            root.visibility = if (isVisible) View.GONE else View.VISIBLE
            tvErrorDesc.text = message
        }
    }

    override fun onDestroyView() {
        newsAdapter = null
        super.onDestroyView()
    }
}