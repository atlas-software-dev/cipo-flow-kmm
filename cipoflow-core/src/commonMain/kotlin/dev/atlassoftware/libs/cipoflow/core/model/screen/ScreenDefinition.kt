package dev.atlassoftware.libs.cipoflow.core.model.screen

import dev.atlassoftware.libs.cipoflow.core.model.component.ScreenComponent

/**
 * Represents the complete definition of a single screen.
 * @property id The unique identifier for this screen (e.g., "home", "profile").
 * @property title The text to be displayed in the top app bar.
 * @property content The list of UI components that make up the body of the screen.
 */
data class ScreenDefinition(
    override val id: String,
    val title: String,
    override val content: List<ScreenComponent>
): Screen








