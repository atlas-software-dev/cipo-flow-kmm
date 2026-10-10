package dev.atlassoftware.libs.cipoflow.compose.renderer.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text as ComposeText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.atlassoftware.libs.cipoflow.core.model.component.Text

@Composable
fun RenderText(component: Text, modifier: Modifier = Modifier) {
    ComposeText(
        text = component.text,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier.padding(vertical = 4.dp)
    )
}
