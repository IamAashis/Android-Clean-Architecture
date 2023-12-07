package com.android.cleanarchitecture.presentation.quotes

import androidx.lifecycle.viewModelScope
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.domain.randomQuotes.usecase.RandomQuotesUseCase
import com.android.cleanarchitecture.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
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
        state.value = QuotesActivityState.ShowToast(message)
    }

    fun getRandomQuotes() {
        viewModelScope.launch {
            randomQuotesUseCase.execute()
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
}

//use State Flow + sealed class due to cleaner when defining state and safety.
sealed class QuotesActivityState {
    object Init : QuotesActivityState()
    data class IsLoading(val isLoading: Boolean) : QuotesActivityState()
    data class ShowToast(val message: String) : QuotesActivityState()
    data class ShowError(val message: List<RandomQuotesResponse>?) : QuotesActivityState()
    data class ResponseData(val response: RandomQuotesResponse?) : QuotesActivityState()
}