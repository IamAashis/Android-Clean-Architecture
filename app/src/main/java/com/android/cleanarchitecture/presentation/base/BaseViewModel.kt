package com.android.cleanarchitecture.presentation.base

import androidx.lifecycle.ViewModel
import com.android.cleanarchitecture.utils.enums.ErrorEnum
import com.android.cleanarchitecture.utils.exceptions.UnAuthorizedException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by Aashis on 07,December,2023
 */
abstract class BaseViewModel : ViewModel() {

    private val bstate = MutableStateFlow<BaseState>(BaseState.Init)
    val mbState: StateFlow<BaseState> get() = bstate

    private fun setError(errorEnum: ErrorEnum?, okAction: () -> Unit?) {
        bstate.value = BaseState.OnErrorEnumResponse(errorEnum, okAction)
    }

    protected fun performActionOnException(throwable: Throwable?, okAction: () -> Unit?) {
        when (throwable) {
            is UnAuthorizedException -> {
                handleError(ErrorEnum.SessionExpired, okAction)
            }

            is UnknownHostException -> {
                handleError(ErrorEnum.NoWifi) {
                    okAction()
                }
            }

            is IOException -> {
                handleError(ErrorEnum.NoWifi) {
                    okAction()
                }
            }
        }
    }

    private fun handleError(errorEnum: ErrorEnum?, okAction: () -> Unit?) {
        setError(errorEnum, okAction)
    }

}

sealed class BaseState {
    object Init : BaseState()
    data class OkAction(val message: String) : BaseState()
    data class OnErrorEnumResponse(val errorEnum: ErrorEnum?, val okActionValue: () -> Unit?) :
        BaseState()
}