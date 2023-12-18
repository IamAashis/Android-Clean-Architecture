package com.android.cleanarchitecture.presentation.quotes

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.domain.randomQuotes.usecase.RandomQuotesUseCase
import com.android.cleanarchitecture.presentation.base.BaseState
import com.android.cleanarchitecture.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Aashis on 05,December,2023
 */
@HiltViewModel
class QuotesViewModel @Inject constructor(private val randomQuotesUseCase: RandomQuotesUseCase) :
    BaseViewModel() {

    private val state = MutableStateFlow<QuotesActivityState>(QuotesActivityState.Init)
    val mState: StateFlow<QuotesActivityState> get() = state

    private fun setLoading() {
        state.value = QuotesActivityState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = QuotesActivityState.IsLoading(false)
    }

    private fun showToast(message: String) {
        bstate.value = BaseState.ShowToast(message)
    }

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }.flowOn(Dispatchers.Main)

    init {
//        collectFlow()
        collectFlowReduce()
    }

    fun collectFlow() {
        viewModelScope.launch {
            val count = countDownFlow
                /* .filter { time ->
                     time % 2 == 0
                 }*/
                .map { time ->
                    time * time
                }
                .onEach { time ->
                    Log.d("cm", "onEach $time")
                }
                .count {
                    it % 3 == 0
                }
            /*.collect { time ->
//                delay(1500L)
                Log.d("cm", "The time is $time")
            }*/

            Log.d("cm", count.toString())
        }
    }

    fun collectFlowReduce() {
        viewModelScope.launch {
            val reduceResult = countDownFlow
                .fold(100) { accumulator, value ->
                    accumulator + value
                }/*.reduce { accumulator, value ->
                    accumulator - value
                }*/

            Log.d("cm", reduceResult.toString())
        }
    }

    fun getRandomQuotes() {
        viewModelScope.launch {
            randomQuotesUseCase.execute().onStart {
                setLoading()
            }.catch {
                showToast(it.message.toString())
                hideLoading()
            }.collect { response ->
                hideLoading()
                when (response) {
                    is BaseResponse.Error -> performActionOnException(response.throwable) {}
//                         state.value =   QuotesActivityState.ShowError(response.data)

                    is BaseResponse.Success -> state.value =
                        QuotesActivityState.ResponseData(response.data?.get(0))

                    else -> {

                    }
                }
            }
        }
    }

    fun getRandomQuotes2() {
        viewModelScope.launch {
            randomQuotesUseCase.executeParallel().onStart {
                setLoading()
            }.catch {
                showToast(it.message.toString())
                hideLoading()
            }.collect { response ->
                hideLoading()
                when (response) {
                    is BaseResponse.Error -> performActionOnException(response.throwable) {}
//                         state.value =   QuotesActivityState.ShowError(response.data)

                    is BaseResponse.Success -> state.value =
                        QuotesActivityState.ResponseData(response.data?.get(0))

                    else -> {

                    }
                }
            }
        }
    }

    fun getQuotesUsingAsync() {
        viewModelScope.launch {
            randomQuotesUseCase.executeAsyncDataUsingChannelFlow()
                .onStart {
                    setLoading()
                }.catch {
                    showToast(it.message.toString())
                    hideLoading()
                }.collect { response ->
                    hideLoading()
                    when (response) {
                        is BaseResponse.Error -> performActionOnException(response.throwable) {}
//                         state.value =   QuotesActivityState.ShowError(response.data)

                        is BaseResponse.Success -> state.value =
                            QuotesActivityState.ResponseData(response.data?.get(0))

                        else -> {

                        }
                    }
                }
        }
    }

    fun getQuotesHere() {
        viewModelScope.launch {
            randomQuotesUseCase.fetchMultipleData(
                result = {
                    when (it) {
                        is BaseResponse.Success -> state.value =
                            QuotesActivityState.ResponseData(it.data?.get(0))

                        is BaseResponse.Error -> performActionOnException(it.throwable) {}

                        else -> {}
                    }
                },
                error = {}
            )
        }
    }

    /* fun getQuotesFromJobs() {
         viewModelScope.launch(Dispatchers.IO) {
             randomQuotesUseCase.executeJobs().onSuccess { response ->
 //                QuotesActivityState.ResponseData(response?.get(0)?.copy())
                 Log.d("cm", "Here from view Model" + response.toString())
             }.onFailure {
                 Log.d("cm", "Error from view model" + it.toString())
             }
         }
     }*/

}

//use State Flow + sealed class due to cleaner when defining state and safety.
sealed class QuotesActivityState {
    object Init : QuotesActivityState()
    data class IsLoading(val isLoading: Boolean) : QuotesActivityState()
    data class ShowError(val message: List<RandomQuotesResponse>?) : QuotesActivityState()
    data class ResponseData(val response: RandomQuotesResponse?) : QuotesActivityState()
    data class ResponseDataTestJob(val responseJobs: RandomQuotesResponse?) : QuotesActivityState()
}