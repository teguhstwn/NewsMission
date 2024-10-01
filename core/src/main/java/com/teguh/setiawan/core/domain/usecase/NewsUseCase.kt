package com.teguh.setiawan.core.domain.usecase

import com.teguh.setiawan.core.data.Resource
import com.teguh.setiawan.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(): Flow<Resource<List<News>>>

    fun getFavoriteNews(): Flow<List<News>>

    fun setFavoriteNews(news: News, state: Boolean)
}