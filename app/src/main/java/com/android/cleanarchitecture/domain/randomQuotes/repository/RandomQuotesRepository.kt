package com.android.cleanarchitecture.domain.randomQuotes.repository

import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.presentation.base.BaseRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Aashis on 05,December,2023
 */
interface RandomQuotesRepository {
    suspend fun getRandomQuotes(): Flow<BaseResponse<List<RandomQuotesResponse>>>
    suspend fun getParallelQuotes(): Flow<BaseResponse<List<RandomQuotesResponse>>>
}