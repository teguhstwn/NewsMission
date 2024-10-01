package com.teguh.setiawan.newsmission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.teguh.setiawan.core.data.Resource
import com.teguh.setiawan.newsmission.R
import com.teguh.setiawan.newsmission.databinding.FragmentHomeBinding
import com.teguh.setiawan.newsmission.ui.utils.Common.navigateToDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.Duration.Companion.seconds

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding<FragmentHomeBinding>()
    private val homeViewModel: HomeViewModel by viewModel()
    private var newsAdapter: HomeAdapter? = null
    private var doubleBackToExitPressedOnce = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.black)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )

        setHomeAdapter()
        setData()
    }

    private fun setHomeAdapter(){

        newsAdapter = HomeAdapter{
            navigateToDetail(it)
        }
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun setData() {
        homeViewModel.news.observe(viewLifecycleOwner) { news ->
            if (news != null) {
                when (news) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        newsAdapter?.submitList(news.data)
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        binding.layoutError.root.isVisible = true
                        binding.layoutError.tvErrorDesc.text =
                            news.message ?: getString(R.string.tvSomethingWrong)
                    }
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.pbLoading.isVisible = isShow
    }

    private val backPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (doubleBackToExitPressedOnce) {
                requireActivity().finish()
                return
            }

            doubleBackToExitPressedOnce = true
            Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()

            lifecycleScope.launch {
                delay(2.seconds)
                doubleBackToExitPressedOnce = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        newsAdapter = null
        binding.rvNews.adapter = null
        doubleBackToExitPressedOnce = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsAdapter = null
        binding.rvNews.adapter = null
        doubleBackToExitPressedOnce = false
    }
}


