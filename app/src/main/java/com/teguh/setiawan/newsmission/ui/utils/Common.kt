package com.teguh.setiawan.newsmission.ui.utils

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.teguh.setiawan.core.domain.model.News
import com.teguh.setiawan.newsmission.R
import com.teguh.setiawan.newsmission.ui.detail.DetailFragment

object Common {
    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(this)
    }


    fun Fragment.navigateToDetail(data: News) {
        val bundle = Bundle()
        bundle.putParcelable(DetailFragment.EXTRA_DATA, data)
        this.findNavController().navigate(R.id.detailFragment, bundle)
    }
}