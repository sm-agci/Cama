package com.cieslak.cama.navigation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.cieslak.cama.networking.ApiClient
import com.cieslak.cama.presentation.registration.RegistrationComponent
import com.cieslak.cama.presentation.todo.ToDoMainComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
    private val client: ApiClient,
    private val prefs: DataStore<Preferences>,
    private val onFinish: () -> Unit,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = if (isLogin()) Configuration.ToDoMain else Configuration.Registration,
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
                    prefs = prefs,
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

    private fun isLogin(): Boolean {
        return runBlocking {
            prefs.data.map { it[stringPreferencesKey(AUTH_KEY)] != null }.first()
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

    companion object {
        const val AUTH_KEY = "AUTH_KEY"
    }
}
