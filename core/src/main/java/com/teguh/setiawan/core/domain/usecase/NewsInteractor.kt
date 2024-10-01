package com.teguh.setiawan.core.domain.usecase

import com.teguh.setiawan.core.domain.model.News
import com.teguh.setiawan.core.domain.repository.INewsRepository

class NewsInteractor(private val newsRepository: INewsRepository): NewsUseCase {

    override fun getAllNews() = newsRepository.getAllNews()

    override fun getFavoriteNews() = newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: News, state: Boolean) = newsRepository.setFavoriteNews(news, state)
}