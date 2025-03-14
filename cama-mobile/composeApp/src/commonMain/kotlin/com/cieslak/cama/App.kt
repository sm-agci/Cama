package com.cieslak.cama

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.cieslak.cama.navigation.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.cieslak.cama.presentation.registration.Registration
import com.cieslak.cama.presentation.todo.ToDoMain

@Composable
@Preview
fun App(root: RootComponent) {
    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Registration -> Registration(instance.component)
                is RootComponent.Child.ToDoMain -> ToDoMain(instance.component)
            }
        }
    }
}
