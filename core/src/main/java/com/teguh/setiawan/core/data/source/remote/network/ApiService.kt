package com.teguh.setiawan.core.data.source.remote.network

import com.teguh.setiawan.core.BuildConfig
import com.teguh.setiawan.core.data.source.remote.response.ListNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything")
    suspend fun getList(
        @Query("q") query: String = "japan",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): ListNewsResponse
}