package dev.atlassoftware.libs.cipoflow.compose.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import dev.atlassoftware.libs.cipoflow.compose.voyager.VoyagerScreen
import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition

@Composable
fun CipoFlowComposeApp(
    block: CipoFlowAppBuilder.() -> Unit) {
    val app = remember { CipoFlowAppBuilder().apply(block).build() }
    val screenRegistry = remember(app.screens) {
        app.screens.associateBy { it.id }
    }
    val screenCache = remember { mutableMapOf<String, Screen>() }
    val screenProvider = remember(screenCache) {
        { screenId: String ->
            // Se a tela já existe no cache, a retorna.
            // Se não, cria uma nova, a guarda no cache, e então a retorna.
            screenCache.getOrPut(screenId) {
                VoyagerScreen(screenId)
            }
        }
    }
    CompositionLocalProvider(
        LocalScreenRegistry provides screenRegistry,
        LocalScreenProvider provides screenProvider
    ) {
        val startScreen = remember { screenProvider(app.getStartScreen().id) }
        Navigator(startScreen)
    }
}


class CipoFlowAppBuilder {
    private val screens = mutableListOf<ScreenDefinition>()
    var startScreenId = ""
    fun screens(vararg screenList: ScreenDefinition) {
        screens.addAll(screenList)
    }
    fun build(): CipoFlowComposeAppDefinition = CipoFlowComposeAppDefinition(screens,startScreenId)
}