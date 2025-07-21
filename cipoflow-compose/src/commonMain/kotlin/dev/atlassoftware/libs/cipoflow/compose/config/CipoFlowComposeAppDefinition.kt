package dev.atlassoftware.libs.cipoflow.compose.config

import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition

data class CipoFlowComposeAppDefinition(
    val screens: List<ScreenDefinition>,
    val startScreenId: String
) {


    /**
     * Helper function to safely get the starting screen.
     * It prioritizes the defined startScreenId, but falls back to the first
     * screen in the list to prevent crashes.
     */
    fun getStartScreen(): ScreenDefinition {
        return screens.find { it.id == startScreenId }
            ?: screens.firstOrNull()
            ?: throw IllegalStateException("App definition must contain at least one screen.")
    }
}