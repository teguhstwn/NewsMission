package com.teguh.setiawan.core.data

import com.teguh.setiawan.core.data.source.local.LocalDataSource
import com.teguh.setiawan.core.data.source.remote.RemoteDataSource
import com.teguh.setiawan.core.data.source.remote.network.ApiResponse
import com.teguh.setiawan.core.data.source.remote.response.NewsResponse
import com.teguh.setiawan.core.domain.model.News
import com.teguh.setiawan.core.domain.repository.INewsRepository
import com.teguh.setiawan.core.utils.AppExecutors
import com.teguh.setiawan.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val remoteSource: RemoteDataSource,
    private val localSource: LocalDataSource,
    private val executors: AppExecutors
) : INewsRepository {

    override fun getAllNews(): Flow<Resource<List<News>>> =
        object : NetworkBoundResource<List<News>, List<NewsResponse>>() {
            override fun fetchFromDB(): Flow<List<News>> {
                return localSource.getAllNews().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetchFromNetwork(data: List<News>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun makeApiCall(): Flow<ApiResponse<List<NewsResponse>>> =
                remoteSource.getAllNews()

            override suspend fun saveApiResult(data: List<NewsResponse>) {
                val newsEntities = DataMapper.mapResponsesToEntities(data)
                localSource.insertNews(newsEntities)
            }
        }.asFlow()

    override fun getFavoriteNews(): Flow<List<News>> {
        return localSource.getFavoriteNews().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteNews(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        executors.diskIO().execute { localSource.setFavoriteNews(newsEntity, state) }
    }
}