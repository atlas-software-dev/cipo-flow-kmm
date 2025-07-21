package dev.atlassoftware.libs.cipoflow.core.model.component

/** A visual container that groups other components under a title. */
data class Section(
    override val id: String,
    val title: String,
    val items: List<ScreenComponent>
) : ScreenComponent