package com.teguh.setiawan.newsmission.ui.detail

import androidx.lifecycle.ViewModel
import com.teguh.setiawan.core.domain.model.News
import com.teguh.setiawan.core.domain.usecase.NewsUseCase

class DetailViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    fun setFavoriteNews(news: News, newStatus:Boolean) {
        newsUseCase.setFavoriteNews(news, newStatus)
    }

}