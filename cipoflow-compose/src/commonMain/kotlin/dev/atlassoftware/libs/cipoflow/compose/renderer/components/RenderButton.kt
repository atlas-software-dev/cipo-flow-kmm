package dev.atlassoftware.libs.cipoflow.compose.renderer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button as ComposeButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.component.Button

@Composable
fun RenderButton(
    component: Button,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    ComposeButton(
        onClick = { component.action?.let(onAction) },
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Text(component.text)
    }
}
