package dev.atlassoftware.libs.cipoflow.compose.config

import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.voyager.core.screen.Screen
import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition

/**
 * Um CompositionLocal que mantém o mapa de todas as definições de tela da aplicação.
 */
val LocalScreenRegistry = compositionLocalOf<Map<String, ScreenDefinition>> {
    error("Nenhum registro de telas (ScreenRegistry) foi fornecido.")
}

/**
 * NOVO: Um CompositionLocal que fornece uma função para obter instâncias de tela
 * cacheadas. Isso permite que qualquer tela solicite outra tela sem conhecer
 * a lógica de cache.
 */
val LocalScreenProvider = compositionLocalOf<(String) -> Screen> {
    error("Nenhum provedor de telas (ScreenProvider) foi fornecido.")
}