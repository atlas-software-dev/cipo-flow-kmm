package dev.atlassoftware.libs.cipoflow.core.model.dsl

import dev.atlassoftware.libs.cipoflow.core.model.component.ScreenComponent
import dev.atlassoftware.libs.cipoflow.core.model.component.Section
import dev.atlassoftware.libs.cipoflow.core.model.component.Spacer
import dev.atlassoftware.libs.cipoflow.core.model.component.Text
import dev.atlassoftware.libs.cipoflow.core.model.component.Button
import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition
import dev.atlassoftware.libs.cipoflow.core.model.action.Action as UiAction

/**
 * DslMarker to prevent improper nesting of DSL builders.
 * For example, you shouldn't be able to define a `screen` inside another `screen`.
 */
@DslMarker
annotation class ScreenDsl

/**
 * The main entry point for the DSL. Creates a [ScreenDefinition].
 *
 * Usage:
 * val homeScreen = screen("home") {
 * title = "Home"
 * content { ... }
 * }
 */
fun screen(id: String, block: ScreenBuilder.() -> Unit): ScreenDefinition {
    return ScreenBuilder(id).apply(block).build()
}

@ScreenDsl
class ScreenBuilder(private val id: String) {
    var title: String = ""
    private val components = mutableListOf<ScreenComponent>()

    /** Defines the body of the screen. */
    fun content(block: ContentBuilder.() -> Unit) {
        components.addAll(ContentBuilder().apply(block).build())
    }

    fun build(): ScreenDefinition = ScreenDefinition(id, title, components)
}

@ScreenDsl
class ContentBuilder {
    private val components = mutableListOf<ScreenComponent>()

    /** Adds a section to the content. */
    fun section(id: String, block: SectionBuilder.() -> Unit) {
        components.add(SectionBuilder(id).apply(block).build())
    }

    /** Adds a button to the content. */
    fun button(id: String, block: ButtonBuilder.() -> Unit) {
        components.add(ButtonBuilder(id).apply(block).build())
    }

    /** Adds a simple text component. */
    fun text(id: String, text: String) {
        components.add(Text(id, text))
    }

    /** Adds vertical spacing. */
    fun spacer(id: String, heightInDp: Int) {
        components.add(Spacer(id, heightInDp))
    }

    fun build(): List<ScreenComponent> = components
}

@ScreenDsl
class SectionBuilder(private val id: String) {
    var title: String = ""
    private val itemsBuilder = ContentBuilder()

    /** Defines the components inside this section. */
    fun items(block: ContentBuilder.() -> Unit) {
        itemsBuilder.apply(block)
    }

    fun build(): Section = Section(id, title, itemsBuilder.build())
}

@ScreenDsl
class ButtonBuilder(private val id: String) {
    var text: String = ""
    var action: UiAction? = null

    /** Sets the action to be performed when the button is clicked. */
    fun onClick(action: UiAction) {
        this.action = action
    }

    fun build(): Button = Button(id, text, action)
}