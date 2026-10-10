package dev.atlassoftware.libs.cipoflow.compose.renderer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.component.Section
import dev.atlassoftware.libs.cipoflow.compose.renderer.ComponentRenderer

@Composable
fun RenderSection(
    component: Section,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = component.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Recursively render the items within the section
            component.items.forEach { item ->
                ComponentRenderer(item, onAction)
            }
        }
    }
}
