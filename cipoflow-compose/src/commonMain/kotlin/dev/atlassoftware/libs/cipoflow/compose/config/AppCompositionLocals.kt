package dev.atlassoftware.libs.cipoflow.compose.config

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition

/**
 * Um CompositionLocal que mantém o mapa de todas as definições de tela da aplicação.
 */
val LocalScreenRegistry = compositionLocalOf<Map<String, ScreenDefinition>> {
    error("Nenhum registro de telas (ScreenRegistry) foi fornecido.")
}

/**
 * Um CompositionLocal que fornece o NavController do Compose Navigation.
 */
val LocalNavController = compositionLocalOf<NavHostController> {
    error("Nenhum NavController foi fornecido.")
}
