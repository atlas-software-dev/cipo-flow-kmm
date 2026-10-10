package dev.atlassoftware.libs.cipoflow.compose.config

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.atlassoftware.libs.cipoflow.compose.renderer.ScreenRenderer
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition
import kotlinx.coroutines.launch

/**
 * Componente principal da biblioteca CipoFlow.
 * 
 * @param definition Uma definição pré-construída da aplicação. Se fornecida, o [block] é ignorado.
 * @param navController O controlador de navegação. Se fornecido, permite controlar a navegação externamente.
 * @param block Bloco DSL para definir as telas e a tela inicial.
 */
@Composable
fun CipoFlowComposeApp(
    definition: CipoFlowComposeAppDefinition? = null,
    navController: NavHostController = rememberNavController(),
    block: (CipoFlowAppBuilder.() -> Unit)? = null
) {
    // Reconstrói a definição sempre que a 'definition' ou o 'block' mudar.
    val app = remember(definition, block) {
        definition ?: CipoFlowAppBuilder().apply(block ?: {}).build()
    }

    val screenRegistry = remember(app.screens) {
        app.screens.associateBy { it.id }
    }
    
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(
        LocalScreenRegistry provides screenRegistry,
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = app.startScreenId
        ) {
            app.screens.forEach { screenDef ->
                composable(route = screenDef.id) {
                    val onAction: (Action) -> Unit = { action ->
                        when (action) {
                            is Action.Navigate -> navController.navigate(action.screenId)
                            is Action.GoBack -> navController.popBackStack()
                            is Action.ShowToast -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = action.message,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }
                    }

                    val canPop = navController.previousBackStackEntry != null

                    ScreenRenderer.Render(
                        screenDefinition = screenDef,
                        showBackButton = canPop,
                        actionDelegate = onAction,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}

class CipoFlowAppBuilder {
    private val screens = mutableListOf<ScreenDefinition>()
    var startScreenId = ""
    fun screens(vararg screenList: ScreenDefinition) {
        screens.addAll(screenList)
    }
    fun build(): CipoFlowComposeAppDefinition = CipoFlowComposeAppDefinition(screens, startScreenId)
}
