package dev.atlassoftware.libs.cipoflow.core.model.component

/** A simple component for displaying non-interactive text. */
data class Text(
    override val id: String,
    val text: String,
) : ScreenComponent