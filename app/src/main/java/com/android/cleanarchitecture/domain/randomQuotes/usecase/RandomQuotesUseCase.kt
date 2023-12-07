package com.android.cleanarchitecture.domain.randomQuotes.usecase

import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResult
import com.android.cleanarchitecture.domain.randomQuotes.repository.RandomQuotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Aashis on 06,December,2023
 */
class RandomQuotesUseCase @Inject constructor(private val repository: RandomQuotesRepository) {
    suspend fun execute(): Flow<BaseResult<RandomQuotesResponse, WrappedResponse<List<RandomQuotesResponse>>>> {
        return repository.getRandomQuotes()
    }
}