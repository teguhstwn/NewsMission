package com.teguh.newsmission.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.teguh.setiawan.core.domain.usecase.NewsUseCase

class BookmarkViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val news = newsUseCase.getFavoriteNews().asLiveData()
}