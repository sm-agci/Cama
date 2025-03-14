package com.cieslak.cama.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.cieslak.cama.networking.ApiClient
import com.cieslak.cama.presentation.registration.RegistrationComponent
import com.cieslak.cama.presentation.todo.ToDoMainComponent
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
    private val client: ApiClient,
    private val onFinish: () -> Unit,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.Registration,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Configuration,
        context: ComponentContext,
    ): Child {
        return when (config) {
            Configuration.Registration -> Child.Registration(
                RegistrationComponent(
                    componentContext = context,
                    client = client,
                    onNavigateToToDo = { navigation.pushNew(Configuration.ToDoMain) }
                )
            )

            is Configuration.ToDoMain -> Child.ToDoMain(
                ToDoMainComponent(
                    componentContext = context,
                    onGoBack = { onFinish() }
                )
            )
        }
    }

    sealed class Child {
        data class Registration(val component: RegistrationComponent) : Child()
        data class ToDoMain(val component: ToDoMainComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object Registration : Configuration()

        @Serializable
        data object ToDoMain : Configuration()
    }
}
