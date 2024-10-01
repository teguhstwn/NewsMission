package com.teguh.setiawan.newsmission.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.teguh.setiawan.core.domain.model.News
import com.teguh.setiawan.core.utils.DateUtils
import com.teguh.setiawan.newsmission.R
import com.teguh.setiawan.newsmission.databinding.FragmentDetailBinding
import com.teguh.setiawan.newsmission.ui.utils.Common.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding<FragmentDetailBinding>()
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.black)

        handleToolbar()
        getBundleData()
    }

    private fun handleToolbar() {
        binding.toolbarDetail.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getBundleData() {
        setDetailNewsInformation(arguments?.getParcelable(EXTRA_DATA))
    }

    private fun setDetailNewsInformation(news: News?) {
        binding.apply {
            news?.let { data ->
                ivNews.loadImage(data.urlToImage)
                tvNewsTitle.text = data.title
                tvNewsDetail.text = data.description
                tvNewsTime.text = data.publishedAt?.let { date -> DateUtils.formatDate(date) }
                tvNewsSource.text = data.author

                var statusFavorite = data.isFavorite
                setFavoriteCharacter(statusFavorite)

                fabBookmark.setOnClickListener{
                    statusFavorite = !statusFavorite
                    showToast(
                        if (statusFavorite)
                            getString(R.string.tvSavedToFavorite)
                        else
                            getString(R.string.tvRemovedToFavorite)
                    )
                    detailViewModel.setFavoriteNews(data, statusFavorite)
                    setFavoriteCharacter(statusFavorite)
                }
            }
        }
    }

    private fun setFavoriteCharacter(isFavorite: Boolean) {
        binding.fabBookmark.apply {
            setImageDrawable(
                setFavoriteIcon(
                    if (isFavorite)
                        R.drawable.ic_bookmark
                    else
                        R.drawable.ic_not_bookmark
                )
            )
        }
    }

    private fun setFavoriteIcon(icon: Int): Drawable? = ContextCompat.getDrawable(requireContext(), icon)

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_DATA = "com.teguh.setiawan.ui.detail.EXTRA_DATA"
    }
}