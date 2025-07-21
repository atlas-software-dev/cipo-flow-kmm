package dev.atlassoftware.libs.cipoflow.core.model.component

import dev.atlassoftware.libs.cipoflow.core.model.action.Action

/** An interactive button that can trigger an [Action]. */
data class Button(
    override val id: String,
    val text: String,
    val action: Action? = null
) : ScreenComponent