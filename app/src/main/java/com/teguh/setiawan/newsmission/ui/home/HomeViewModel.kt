package com.teguh.setiawan.newsmission.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.teguh.setiawan.core.domain.usecase.NewsUseCase

class HomeViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()
}