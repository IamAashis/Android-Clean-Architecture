package com.android.cleanarchitecture.presentation.quotes

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.android.cleanarchitecture.R
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.databinding.ActivityQuotesBinding
import com.android.cleanarchitecture.presentation.base.BaseActivity
import com.android.cleanarchitecture.utils.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RandomQuotesActivity : BaseActivity<ActivityQuotesBinding, QuotesViewModel>() {
    private val randomQuotesViewModel: QuotesViewModel by viewModels()
    override fun getViewBinding() = ActivityQuotesBinding.inflate(layoutInflater)
    override fun getViewModel() = randomQuotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        initListener()
        initObserve()
        initListener()
        randomQuotesViewModel.getRandomQuotes()
    }

    private fun initObserve() {
        randomQuotesViewModel.mState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: QuotesActivityState) {
        when (state) {
            is QuotesActivityState.Init -> Unit
            is QuotesActivityState.ShowToast -> showToast(state.message)
            is QuotesActivityState.IsLoading -> handleLoading(state.isLoading)
            is QuotesActivityState.ResponseData -> {
                setResponse(state.response)
            }

            else -> {}
        }
    }

    private fun setResponse(response: RandomQuotesResponse?) {
//        pref.saveQuotes(response?.content)
        binding?.txvQuotes?.text = getString(R.string.quotes, response?.content)
        binding?.txvAuthor?.text = getString(R.string.author_name, response?.author)
//        Log.d("cm", pref.getQuotes())
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding?.txvQuotes?.visibility = View.GONE
            binding?.txvAuthor?.visibility = View.GONE
            binding?.prbQuotes?.visibility = View.VISIBLE
        } else {
            binding?.prbQuotes?.visibility = View.GONE
            binding?.txvQuotes?.visibility = View.VISIBLE
            binding?.txvAuthor?.visibility = View.VISIBLE
        }
    }

    private fun initListener() {
        binding?.apply {
            imvReload.setOnClickListener {
//                randomQuotesViewModel.getRandomQuotes()
                randomQuotesViewModel.getRandomQuotes2()
            }
        }
    }
}