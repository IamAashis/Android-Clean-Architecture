package com.android.cleanarchitecture.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected var binding: VB? = null
    abstract fun getViewBinding(): VB

    abstract fun getViewModel(): VM

    private var viewModel: VM? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
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
        DialogUtils.showAlertDialog(context,
            getString(title ?: 0),
            getString(message ?: 0),
            { okAction?.invoke() },
            {})
    }
}
