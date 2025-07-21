package dev.atlassoftware.libs.cipoflow.compose.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import dev.atlassoftware.libs.cipoflow.compose.voyager.VoyagerScreen
import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition

@Composable
fun CipoFlowComposeApp(
    block: CipoFlowAppBuilder.() -> Unit) {
    val app = remember { CipoFlowAppBuilder().apply(block).build() }
    val screenCache = remember { mutableMapOf<String, VoyagerScreen>() }
    val screenProvider = remember(app, screenCache) {
        lateinit var provider: (String) -> VoyagerScreen
        provider = { screenId ->
            screenCache.getOrPut(screenId) {
                val definition = app.screens.find { it.id == screenId }
                    ?: throw IllegalArgumentException("Screen with id '$screenId' not found in app definition.")
                // Passa a referência da própria lambda para a tela, permitindo a navegação futura.
                VoyagerScreen(definition, provider)
            }
        }
        provider
    }
    val firstScreen = remember(app, screenProvider) { screenProvider(app.getStartScreen().id) }
    Navigator(firstScreen)
}


class CipoFlowAppBuilder {
    private val screens = mutableListOf<ScreenDefinition>()
    var startScreenId = ""
    fun screens(vararg screenList: ScreenDefinition) {
        screens.addAll(screenList)
    }
    fun build(): CipoFlowComposeAppDefinition = CipoFlowComposeAppDefinition(screens,startScreenId)
}