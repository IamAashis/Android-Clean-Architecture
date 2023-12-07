package com.android.cleanarchitecture.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.android.cleanarchitecture.R
import com.android.cleanarchitecture.utils.enums.ErrorEnum
import com.android.cleanarchitecture.utils.util.DialogUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Aashis on 07,December,2023
 */

abstract class BaseActivity<VB : ViewBinding, BVM : BaseViewModel> : AppCompatActivity() {
    protected var binding: VB? = null
    abstract fun getViewBinding(): VB
    abstract fun getViewModel(): BVM
    private var viewModel: BVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        binding = getViewBinding()
        setContentView(binding?.root)
        setup()
    }

    private fun setup() {
        initObservers()
    }

    private fun initObservers() {
        viewModel?.mbState?.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            ?.onEach { state -> handleStateChange(state) }?.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: BaseState) {
        when (state) {
            is BaseState.Init -> Unit
            is BaseState.OnErrorEnumResponse -> {
                showMessageDialog(state.errorEnum, state.okActionValue)
            }

            is BaseState.OkAction -> {}
        }
    }

    private fun showMessageDialog(errorEnum: ErrorEnum?, okAction: (() -> Unit?)?) {
        when (errorEnum) {
            ErrorEnum.NoWifi -> {
                showMessageDialog(
                    R.string.error_no_wifi, R.string.error_no_wifi_description, okAction
                )
            }

            ErrorEnum.SessionExpired -> {
                showMessageDialog(
                    R.string.session_expired, R.string.session_expired_description, okAction
                )
            }

            ErrorEnum.DefaultError -> {
                showMessageDialog(R.string.error, R.string.error_default, okAction)
            }

            else -> {
                showMessageDialog(
                    R.string.error, R.string.error_default, okAction
                )
            }
        }
    }

    private fun showMessageDialog(title: Int?, message: Int?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(this,
            getString(title ?: 0),
            getString(message ?: 0),
            { okAction?.invoke() },
            {})
    }
}