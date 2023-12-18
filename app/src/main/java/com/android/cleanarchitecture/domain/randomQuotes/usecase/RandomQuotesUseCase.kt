package com.android.cleanarchitecture.domain.randomQuotes.usecase

import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.domain.randomQuotes.repository.RandomQuotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Aashis on 06,December,2023
 */
class RandomQuotesUseCase @Inject constructor(private val repository: RandomQuotesRepository) {
    suspend fun execute(): Flow<BaseResponse<List<RandomQuotesResponse>>> {
        return repository.getRandomQuotes()
    }

    suspend fun executeParallel(): Flow<BaseResponse<List<RandomQuotesResponse>>> {
        return repository.getParallelQuotes()
    }

    suspend fun executeAsyncDataUsingChannelFlow(): Flow<BaseResponse<List<RandomQuotesResponse>>> {
        return repository.getAsyncDataUsingChannelFlow()
    }

    /*suspend fun executeJobs(): BaseResponse<List<RandomQuotesResponse>> {
        return repository.getQuotesWithJobs()
    }*/

    suspend fun fetchMultipleData(
        result: suspend (randomQuotesResponse: BaseResponse<List<RandomQuotesResponse>>) -> Unit,
        error: suspend (checkout: String) -> Unit,
    ) {
        repository.fetchMultipleData(result = result, error = error)
    }
}