package com.android.cleanarchitecture.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cleanarchitecture.R
import com.android.cleanarchitecture.data.login.model.request.LoginRequest
import com.android.cleanarchitecture.data.login.model.request.LoginResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.domain.login.usecase.LoginUseCase
import com.android.cleanarchitecture.utils.extension.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val state = MutableStateFlow<LoginActivityState>(LoginActivityState.Init)

    val mState: StateFlow<LoginActivityState> get() = state

    private fun setLoading() {
        state.value = LoginActivityState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = LoginActivityState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = LoginActivityState.ShowToast(message)
    }

    fun login(loginRequest: LoginRequest) {
        if (validate(loginRequest.email, loginRequest.password)) {
            viewModelScope.launch {
                loginUseCase.execute(loginRequest)
                    .onStart {
                        setLoading()
                    }
                    .catch { exception ->
                        hideLoading()
                        showToast(exception.message.toString())
                    }.collect { baseResult ->
                        hideLoading()
                        when (baseResult) {
                            is BaseResponse.Error -> state.value =
                                LoginActivityState.ErrorLogin(baseResult.data)

                            is BaseResponse.Success -> state.value =
                                LoginActivityState.SuccessLogin(baseResult.data)

                            else -> {}
                        }
                    }
            }
        }
    }
}

fun validate(email: String?, password: String?): Boolean {
    if (email?.isEmail() == true) {
        LoginActivityState.ShowLoginError("email", R.string.error_email_not_valid)
        return false
    }

    if ((password?.length ?: 0) < 8) {
        LoginActivityState.ShowLoginError("password", R.string.error_password_not_valid)
        return false
    }

    return true
}

sealed class LoginActivityState {
    object Init : LoginActivityState()
    data class IsLoading(val isLoading: Boolean) : LoginActivityState()
    data class ShowToast(val message: String) : LoginActivityState()
    data class SuccessLogin(val loginEntity: LoginResponse?) : LoginActivityState()
    data class ShowLoginError(val errorField: String, val errorMessage: Int) :
        LoginActivityState()

    data class ErrorLogin(val rawResponse: LoginResponse?) : LoginActivityState()
}