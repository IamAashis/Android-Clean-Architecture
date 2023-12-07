package com.android.cleanarchitecture.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.login.remote.dto.LoginResponse
import com.android.cleanarchitecture.databinding.ActivityLoginBinding
import com.android.cleanarchitecture.domain.login.model.LoginEntity
import com.android.cleanarchitecture.util.extension.showAlertDialog
import com.android.cleanarchitecture.util.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        initListener()
        initObserve()
    }

    private fun initObserve() {
        viewModel.mState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: LoginActivityState) {
        when (state) {
            is LoginActivityState.Init -> Unit
            is LoginActivityState.ErrorLogin -> handleErrors(state.rawResponse)
            is LoginActivityState.SuccessLogin -> handleSuccess(state.loginEntity)
            is LoginActivityState.ShowToast -> showToast(state.message)
            is LoginActivityState.IsLoading -> handleLoading(state.isLoading)
            is LoginActivityState.ShowLoginError -> validateEmailPassword(
                state.errorField, state.errorMessage
            )

            else -> {}
        }
    }

    private fun validateEmailPassword(errorField: String, errorMessage: Int) {

    }

    private fun handleLoading(loading: Boolean) {

    }

    private fun handleSuccess(loginEntity: LoginEntity?) {

    }

    private fun handleErrors(rawResponse: WrappedResponse<LoginResponse>) {
        showAlertDialog(rawResponse.message)
    }

    private fun initListener() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

//            viewModel.login(LoginRequest(email = email, password = password))
        }
    }
}