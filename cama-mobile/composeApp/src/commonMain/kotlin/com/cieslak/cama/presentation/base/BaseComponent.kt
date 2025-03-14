package com.cieslak.cama.presentation.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseComponent<EVENT, CONTENT_STATE> {
    private val mutableStateFlow: MutableStateFlow<CONTENT_STATE> by lazy { MutableStateFlow(defaultContentState) }

    val contentState: StateFlow<CONTENT_STATE>
        get() = mutableStateFlow.asStateFlow()

    protected abstract val defaultContentState: CONTENT_STATE

    private fun getCurrentState() : CONTENT_STATE = mutableStateFlow.value ?: defaultContentState

    protected fun modify(inlined: CONTENT_STATE.() -> CONTENT_STATE) {
        mutableStateFlow.value = inlined(getCurrentState())
    }

    abstract fun onEvent(event: EVENT)
}
