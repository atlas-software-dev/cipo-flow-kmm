package dev.atlassoftware.libs.cipoflow.core.model.component

import dev.atlassoftware.libs.cipoflow.core.model.action.Action

/**
 * Interface representing the type of input.
 * Extensible for various field types like Text, Number, Date, etc.
 */
sealed interface InputType {
    /** Text input type, with optional configuration for single line, max lines, or password masking. */
    data class Text(
        val isSingleLine: Boolean = true,
        val maxLines: Int = 1,
        val isPassword: Boolean = false,
    ) : InputType

    /** Integer or Decimal number input type. */
    data class Number(
        val isDecimal: Boolean = false,
    ) : InputType

    /** Date input type with configurable format pattern. */
    data class Date(
        val format: String = "yyyy-MM-dd",
    ) : InputType
}

/**
 * An interactive form input component.
 *
 * @param id Unique identifier serving as the form field key/name.
 * @param label Optional title/label for the field.
 * @param placeholder Optional hint text shown when empty.
 * @param initialValue The starting text value.
 * @param inputType The specific type of input entry (Text, Number, Date, etc.).
 * @param isRequired Indicates if this field is required.
 * @param isEnabled Indicates if this input is enabled for interaction.
 * @param isReadOnly Indicates if this input is read-only.
 * @param errorMessage Optional error message to display below the input.
 * @param helperText Optional helper/caption text to display below the input.
 * @param onValueChangeAction Optional Action to trigger when the input value changes.
 */
data class Input(
    override val id: String,
    val label: String? = null,
    val placeholder: String? = null,
    val initialValue: String = "",
    val inputType: InputType = InputType.Text(),
    val isRequired: Boolean = false,
    val isEnabled: Boolean = true,
    val isReadOnly: Boolean = false,
    val errorMessage: String? = null,
    val helperText: String? = null,
    val onValueChangeAction: Action? = null,
) : ScreenComponent
