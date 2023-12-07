package com.android.cleanarchitecture.data.common.module

import android.content.Context
import com.android.cleanarchitecture.utils.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by Aashis on 05,December,2023
 */
@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {
    @Provides
    fun providesSharedPref(@ApplicationContext context: Context): SharedPrefs {
        return SharedPrefs(context)
    }
}