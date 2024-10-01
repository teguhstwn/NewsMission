package com.teguh.setiawan.newsmission.di

import com.teguh.setiawan.core.domain.usecase.NewsInteractor
import com.teguh.setiawan.core.domain.usecase.NewsUseCase
import com.teguh.setiawan.newsmission.ui.detail.DetailViewModel
import com.teguh.setiawan.newsmission.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}