package com.android.cleanarchitecture.data.randomQuotes.repository

import android.util.Log
import com.android.cleanarchitecture.data.common.network.ApiService
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.domain.randomQuotes.repository.RandomQuotesRepository
import com.android.cleanarchitecture.presentation.base.BaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Aashis on 04,December,2023
 */
class RandomQuotesRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RandomQuotesRepository, BaseRepository() {

    override suspend fun getRandomQuotes(): Flow<BaseResponse<List<RandomQuotesResponse>>> {
        return flow {
            try {
                val response = apiService.randomQuotes(/*"https://api.quotable.io/quotes/random"*/)
                if (response.isSuccessful) {
                    val body = response.body()!!
                    emit(BaseResponse.Success(body))
                } else {
                    emit(
                        BaseResponse.Error(
                            getError(
                                response.code(), response.errorBody()?.string()
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                emit(BaseResponse.Error(getError(e)))
            }
        }
    }

    override suspend fun fetchMultipleData(
        result: suspend (randomQuotesResponse: BaseResponse<List<RandomQuotesResponse>>) -> Unit,
        error: suspend (checkout: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response2 = async { apiService.randomQuotes2("https://api.kanye.rest/") }

                val responseTwo = response2.await()

                if (responseTwo.isSuccessful) {
                    val response1 = apiService.randomQuotes()
                    result(BaseResponse.Success(response1.body()))
                }
            } catch (e: Exception) {
                Log.d("cm", "in catch section" + e.toString())
            }
        }
    }

    override suspend fun getParallelQuotes(): Flow<BaseResponse<List<RandomQuotesResponse>>> {
        return flow {
            coroutineScope {
                try {
                    val response2 =
                        async(Dispatchers.IO) { apiService.randomQuotes2("https://api.kanye.rest/") }

                    val responseTwo = response2.await()

                    if (responseTwo.isSuccessful) {
                        val response1 = apiService.randomQuotes()
                        emit(BaseResponse.Success(response1.body()))
//                        send(BaseResponse.Success(response1.body()))
                    }
                } catch (e: Exception) {
                    Log.d("cm", "in catch section" + e.toString())
                }
            }
        }
    }

    /*  override suspend fun getAsyncQuotes(): Flow<BaseResponse<List<RandomQuotesResponse>>> {
          return flow {
              try {
                  CoroutineScope(Dispatchers.IO).launch {
                      val response2 = async { apiService.randomQuotes2("https://api.kanye.rest/") }

                      val responseOneTest = response2.await()

                      if (responseOneTest.isSuccessful) {
                          val response1 = apiService.randomQuotes()

                          emit(BaseResponse.Success(response1.body()))
                      }
                  }
              } catch (e: Exception) {
                  emit(BaseResponse.Error(getError(e)))
              }
          }
      }*/

    override suspend fun getAsyncDataUsingChannelFlow(): Flow<BaseResponse<List<RandomQuotesResponse>>> =
        channelFlow {
            try {
                val response2 = async { apiService.randomQuotes2("https://api.kanye.rest/") }

                val responseOneTest = response2.await()

                if (responseOneTest.isSuccessful) {
                    val response1 = apiService.randomQuotes()

                    send(BaseResponse.Success(response1.body()))
                }
                close() // Close the channel after sending the value
            } catch (e: Exception) {
                send(BaseResponse.Error(getError(e)))
                close(e) // Close the channel with an exception if an error occurs
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getApiCallAsync() {

        // job.join()  It await the suspend state and complete after the job is complete, we can use this job function to await async and many more task in it
        // in the job.join we have execute the coroutine and wait until the coroutine is finished.

        val job1 = CoroutineScope(Dispatchers.IO).launch {// Launch
            apiService.randomQuotes()
        }

        val job2 = CoroutineScope(Dispatchers.IO).async {// Launch
            apiService.randomQuotes2("https://api.kanye.rest/")
        }

        job1.join()
        job2.join() // Here we are waiting to complete response of job1 and job2 before moving to next line.

        // Launch - When yo donot care about result.(Fire & Forget).
        // Async - when you expect result/output from your coroutine
//                As Both can be used to achieve the same functionlality but it is better to use things that are meant for it.
        //Describle pratical example for better

        val job3 = CoroutineScope(Dispatchers.IO).async {// Launch
            apiService.randomQuotes()
        }

        val job4 = CoroutineScope(Dispatchers.IO).async {// Launch
            apiService.randomQuotes2("https://api.kanye.rest/")
        }

        Log.d("getResult", job3.await().toString() + job4.await().toString())

        // Here we are waiting to complete response of job1 and job2 before moving to next line.
    }

    suspend fun launchAndAsync() {
        val job1 =
            CoroutineScope(Dispatchers.IO).launch {// This is launch function where we don't care about the end result
                async { apiService.randomQuotes() }
            }

        job1.join() // In here it wait the response and execute new code after the api call in complete
        Log.d("launch", job1.toString())

//        #Here is another better way to write about it
        val job2 = CoroutineScope(Dispatchers.IO).async {
            apiService.randomQuotes()
        }
//  In here are performing the code as same as the above, we can see in here code are minimize
        Log.d("launch", job2.await().toString())
    }

    suspend fun parallelApiCall() {
        CoroutineScope(Dispatchers.IO).launch {
            val result1 = async { apiService.randomQuotes() }
            val result2 = async { apiService.randomQuotes2("") }

            Log.d("apiCall", result1.await().toString() + result2.await().toString())
        }
    }
}

