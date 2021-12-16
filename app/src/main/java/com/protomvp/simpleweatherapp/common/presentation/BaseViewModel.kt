package com.protomvp.simpleweatherapp.common.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

interface ViewModelState {
    companion object None : ViewModelState
}

interface ViewModelIntent

interface SideEffect {
    companion object None : SideEffect
}

@ExperimentalCoroutinesApi
abstract class BaseViewModel<
        S : ViewModelState,
        I : ViewModelIntent,
        SE : SideEffect> : ViewModel() {

    private var stateFloW = MutableStateFlow<ViewModelState?>(null)

    private var sideEffectFlow = MutableStateFlow<SideEffect?>(null)

    abstract fun sendIntent(intent: I)

    /**
     * Override this when needed to initialise the view model
     */
    open fun start() {}

    protected fun update(state: S) {
        stateFloW.value = state
    }

    protected fun send(sideEffect: SE) {
        sideEffectFlow.value = sideEffect
    }

    @Suppress("UNCHECKED_CAST")
    fun getStateFlow(): Flow<S> =
        stateFloW
            .filterNotNull()
            .map { it as S }

    @Suppress("UNCHECKED_CAST")
    fun getSideEffectFlow(): Flow<SE> =
        sideEffectFlow
            .filterNotNull()
            .map { it as SE }

    /**
     * Provides easy read only access to the current state
     */
    protected val currentState = stateFloW.value

    protected fun S.updateState() = apply { update(this) }

    protected fun SE.send() = apply { send(this) }

    fun reset() {
        stateFloW = MutableStateFlow(ViewModelState.None)
        sideEffectFlow = MutableStateFlow(SideEffect.None)
    }
}