package dev.atlassoftware.libs.cipoflow.core.model.dsl

import dev.atlassoftware.libs.cipoflow.core.model.component.Button
import dev.atlassoftware.libs.cipoflow.core.model.component.Image
import dev.atlassoftware.libs.cipoflow.core.model.component.ImageAlignment
import dev.atlassoftware.libs.cipoflow.core.model.component.Input
import dev.atlassoftware.libs.cipoflow.core.model.component.InputType
import dev.atlassoftware.libs.cipoflow.core.model.component.ScaleType
import dev.atlassoftware.libs.cipoflow.core.model.component.ScreenComponent
import dev.atlassoftware.libs.cipoflow.core.model.component.Section
import dev.atlassoftware.libs.cipoflow.core.model.component.Spacer
import dev.atlassoftware.libs.cipoflow.core.model.component.Text
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

    /** Adds an image component. */
    fun image(id: String, block: ImageBuilder.() -> Unit) {
        components.add(ImageBuilder(id).apply(block).build())
    }

    /** Adds an input component using a builder. */
    fun input(id: String, block: InputBuilder.() -> Unit) {
        components.add(InputBuilder(id).apply(block).build())
    }

    /** Adds a simple text input component. */
    fun textInput(
        id: String,
        label: String? = null,
        placeholder: String? = null,
        initialValue: String = ""
    ) {
        components.add(
            Input(
                id = id,
                label = label,
                placeholder = placeholder,
                initialValue = initialValue
            )
        )
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

@ScreenDsl
class ImageBuilder(private val id: String) {
    var url: String = ""
    var contentDescription: String? = null
    var scaleType: ScaleType = ScaleType.Fit
    var cornerRadius: Int = 0
    var width: Int? = null
    var height: Int? = null
    var alignment: ImageAlignment = ImageAlignment.Center

    fun build(): Image = Image(
        id = id,
        url = url,
        contentDescription = contentDescription,
        scaleType = scaleType,
        cornerRadius = cornerRadius,
        width = width,
        height = height,
        alignment = alignment
    )
}

@ScreenDsl
class InputBuilder(private val id: String) {
    var label: String? = null
    var placeholder: String? = null
    var initialValue: String = ""
    var inputType: InputType = InputType.Text()
    var isRequired: Boolean = false
    var isEnabled: Boolean = true
    var isReadOnly: Boolean = false
    var errorMessage: String? = null
    var helperText: String? = null
    var onValueChangeAction: UiAction? = null

    /** Sets the input type to Text with custom parameters. */
    fun textType(
        isSingleLine: Boolean = true,
        maxLines: Int = 1,
        isPassword: Boolean = false
    ) {
        inputType = InputType.Text(
            isSingleLine = isSingleLine,
            maxLines = maxLines,
            isPassword = isPassword
        )
    }

    /** Sets the input type to Number (integer or decimal). */
    fun numberType(isDecimal: Boolean = false) {
        inputType = InputType.Number(isDecimal = isDecimal)
    }

    /** Sets the input type to Date with optional format. */
    fun dateType(format: String = "yyyy-MM-dd") {
        inputType = InputType.Date(format = format)
    }

    fun build(): Input = Input(
        id = id,
        label = label,
        placeholder = placeholder,
        initialValue = initialValue,
        inputType = inputType,
        isRequired = isRequired,
        isEnabled = isEnabled,
        isReadOnly = isReadOnly,
        errorMessage = errorMessage,
        helperText = helperText,
        onValueChangeAction = onValueChangeAction
    )
}
