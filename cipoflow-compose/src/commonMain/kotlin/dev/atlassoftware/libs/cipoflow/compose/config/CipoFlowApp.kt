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
    val screenProvider = remember(app) {
        { screenId: String ->
            app.screens.find { it.id == screenId }
                ?: throw IllegalArgumentException("Screen with id '$screenId' not found in app definition.")
        }
    }
    val firstScreen = remember(app) { app.getStartScreen() }
    Navigator(VoyagerScreen(firstScreen, screenProvider))
}


class CipoFlowAppBuilder {
    private val screens = mutableListOf<ScreenDefinition>()
    var startScreenId = ""
    fun screens(vararg screenList: ScreenDefinition) {
        screens.addAll(screenList)
    }
    fun build(): CipoFlowComposeAppDefinition = CipoFlowComposeAppDefinition(screens,startScreenId)
}