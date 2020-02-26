package com.jacquessmuts.overengineered.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.jacquessmuts.overengineered.R
import com.jacquessmuts.overengineered.ui.BaseFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.sharedViewModel
import reactivecircus.flowbinding.android.view.clicks

@FlowPreview
@ExperimentalCoroutinesApi
class MainFragment : BaseFragment<MainState, MainViewModel>() {

    override val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    override fun onStateUpdated(nuState: MainState) {
        message.setText(getString(R.string.cards_remaining, nuState.cardsRemaining))
        Glide.with(this)
            .load(nuState.imageAddress)
            .placeholder(imageView.drawable)
            .fallback(R.drawable.card_back)
            .transition(withCrossFade())
            .into(imageView)
    }

    private fun setListeners() {
        button.clicks()
            .onEach { viewModel.buttonClicked() }
            .launchIn(this)
    }
}
