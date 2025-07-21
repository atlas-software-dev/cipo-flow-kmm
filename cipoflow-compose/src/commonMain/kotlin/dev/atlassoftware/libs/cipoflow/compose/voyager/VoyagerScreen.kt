package dev.atlassoftware.libs.cipoflow.compose.voyager

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.atlassoftware.libs.cipoflow.compose.config.LocalScreenProvider
import dev.atlassoftware.libs.cipoflow.compose.config.LocalScreenRegistry
import dev.atlassoftware.libs.cipoflow.compose.renderer.ScreenRenderer
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition
import kotlinx.coroutines.launch


/**
 * A generic Voyager Screen that can render any screen defined by our low-code DSL.
 *
 * @param screenId The unique ID of the screen to render, which must exist in the [screenRegistry].
 */
data class VoyagerScreen(
    private val screenId: String
) : Screen {
    override val key: String = screenId

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        val screenRegistry = LocalScreenRegistry.current
        val screenProvider = LocalScreenProvider.current

        val screenDefinition = remember(screenId) {
            screenRegistry[screenId]
                ?: throw IllegalArgumentException("Tela com id '$screenId' não encontrada no registro.")
        }

       //  The action handler translates low-code Actions into Voyager/Compose actions.
        val onAction: (Action) -> Unit = { action ->
            when (action) {
                is Action.Navigate -> navigator.push(screenProvider(action.screenId))
                is Action.GoBack -> navigator.pop()
                is Action.ShowToast -> {
                    // Use a Snackbar for a better user experience than println
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = action.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }

        ScreenRenderer.render(
            screenDefinition = screenDefinition,
            showBackButton = navigator.canPop,
            actionDelegate = onAction,
            snackbarHostState = snackbarHostState
        )
    }
}