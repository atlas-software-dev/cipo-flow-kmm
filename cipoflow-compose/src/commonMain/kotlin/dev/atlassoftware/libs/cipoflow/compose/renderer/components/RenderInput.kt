package dev.atlassoftware.libs.cipoflow.compose.renderer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.component.Input
import dev.atlassoftware.libs.cipoflow.core.model.component.InputType

@Composable
fun RenderInput(
    component: Input,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    var textValue by remember(component.id, component.initialValue) {
        mutableStateOf(component.initialValue)
    }

    val keyboardOptions = when (val type = component.inputType) {
        is InputType.Text -> KeyboardOptions(
            keyboardType = if (type.isPassword) KeyboardType.Password else KeyboardType.Text
        )
        is InputType.Number -> KeyboardOptions(
            keyboardType = if (type.isDecimal) KeyboardType.Decimal else KeyboardType.Number
        )
        is InputType.Date -> KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    }

    val visualTransformation = when (val type = component.inputType) {
        is InputType.Text -> if (type.isPassword) PasswordVisualTransformation() else VisualTransformation.None
        else -> VisualTransformation.None
    }

    val singleLine = when (val type = component.inputType) {
        is InputType.Text -> type.isSingleLine
        else -> true
    }

    val maxLines = when (val type = component.inputType) {
        is InputType.Text -> type.maxLines
        else -> 1
    }

    val labelText = component.label?.let { label ->
        if (component.isRequired) "$label *" else label
    }

    val errorMessage = component.errorMessage
    val helperText = component.helperText

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                onAction(Action.InputChanged(component.id, newValue))
                component.onValueChangeAction?.let(onAction)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = component.isEnabled,
            readOnly = component.isReadOnly,
            label = labelText?.let { text -> { Text(text) } },
            placeholder = component.placeholder?.let { placeholder -> { Text(placeholder) } },
            isError = errorMessage != null,
            supportingText = when {
                errorMessage != null -> {
                    { Text(text = errorMessage, color = MaterialTheme.colorScheme.error) }
                }
                helperText != null -> {
                    { Text(text = helperText) }
                }
                else -> null
            },
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
    }
}
