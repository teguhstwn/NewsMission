package com.teguh.setiawan.core.di

import androidx.room.Room
import com.teguh.setiawan.core.BuildConfig
import com.teguh.setiawan.core.data.NewsRepository
import okhttp3.OkHttpClient
import okhttp3.CertificatePinner
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.teguh.setiawan.core.data.source.local.LocalDataSource
import com.teguh.setiawan.core.data.source.local.room.NewsDatabase
import com.teguh.setiawan.core.data.source.remote.RemoteDataSource
import com.teguh.setiawan.core.domain.repository.INewsRepository
import com.teguh.setiawan.core.data.source.remote.network.ApiService
import com.teguh.setiawan.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("teguhsetiawan".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            "NewsMission.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
    factory { get<NewsDatabase>().newsDao() }
}

val networkModule = module {
    single {
        val hostname = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/UHYOXs5BxRVKGG7ykhBYGxgne9rRrDaUTXC1MpEtwZU=")
            .add(hostname, "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
            .add(hostname, "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .add(hostname, "sha256/K87oWBWM9UZfyddvDfoxL+8lpNyoUB2ptGtn0fv6G2Q=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
            .create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<INewsRepository> { NewsRepository(get(), get(), get()) }
}
