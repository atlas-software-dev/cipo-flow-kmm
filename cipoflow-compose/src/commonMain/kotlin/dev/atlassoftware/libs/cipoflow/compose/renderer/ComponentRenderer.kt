package dev.atlassoftware.libs.cipoflow.compose.renderer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.component.Button
import dev.atlassoftware.libs.cipoflow.core.model.component.ScreenComponent
import dev.atlassoftware.libs.cipoflow.core.model.component.Section
import dev.atlassoftware.libs.cipoflow.core.model.component.Spacer
import dev.atlassoftware.libs.cipoflow.core.model.component.Text

/**
 * The main renderer function. It takes a UiComponent and an action handler,
 * and recursively renders the component and its children.
 *
 * @param component The UI component model to render.
 * @param onAction A lambda function to be invoked when a user action is triggered.
 */
@Composable
fun ComponentRenderer(component: ScreenComponent, onAction: (Action) -> Unit) {
    when (component) {
        is Text -> {
            Text(
                text = component.text,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        is Button -> {
            Button(
                onClick = { component.action?.let(onAction) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text(component.text)
            }
        }
        is Section -> {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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
        is Spacer -> {
            Spacer(modifier = Modifier.height(component.height.dp))
        }
    }
}
