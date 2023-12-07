package com.android.cleanarchitecture.data.randomQuotes

import com.android.cleanarchitecture.data.common.network.ApiService
import com.android.cleanarchitecture.data.randomQuotes.repository.RandomQuotesRepositoryImpl
import com.android.cleanarchitecture.di.module.NetworkModule
import com.android.cleanarchitecture.domain.randomQuotes.repository.RandomQuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Aashis on 06,December,2023
 */
@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RandomQuotesModule {

    @Singleton
    @Provides
    fun provideQuotesRepository(apiService: ApiService): RandomQuotesRepository {
        return RandomQuotesRepositoryImpl(apiService)
    }
}
