package dev.atlassoftware.libs.cipoflow.core.model.component

/** An invisible component used to add vertical space between other components. */
data class Spacer(
    override val id: String,
    val height: Int
) : ScreenComponent