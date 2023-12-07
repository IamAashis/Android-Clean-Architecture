package com.android.cleanarchitecture.data.login

import com.android.cleanarchitecture.di.module.NetworkModule
import com.android.cleanarchitecture.data.common.network.ApiService
import com.android.cleanarchitecture.data.login.repository.LoginRepositoryImpl
import com.android.cleanarchitecture.data.randomQuotes.repository.RandomQuotesRepositoryImpl
import com.android.cleanarchitecture.domain.login.repository.LoginRepository
import com.android.cleanarchitecture.domain.randomQuotes.repository.RandomQuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Aashis on 04,December,2023
 */
@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginApi: ApiService): LoginRepository {
        return LoginRepositoryImpl(loginApi)
    }

    @Singleton
    @Provides
    fun provideQuotesRepository(apiService: ApiService): RandomQuotesRepository {
        return RandomQuotesRepositoryImpl(apiService)
    }
}