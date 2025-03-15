package com.cieslak.cama.presentation.todo

import com.arkivanov.decompose.ComponentContext

class ToDoMainComponent(
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
): ComponentContext by componentContext {

    fun goBack() {
        onGoBack()
    }
}
