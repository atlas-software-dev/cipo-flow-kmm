package dev.atlassoftware.libs.cipoflow.compose.renderer

import androidx.compose.runtime.Composable
import dev.atlassoftware.libs.cipoflow.compose.renderer.components.RenderButton
import dev.atlassoftware.libs.cipoflow.compose.renderer.components.RenderImage
import dev.atlassoftware.libs.cipoflow.compose.renderer.components.RenderSection
import dev.atlassoftware.libs.cipoflow.compose.renderer.components.RenderSpacer
import dev.atlassoftware.libs.cipoflow.compose.renderer.components.RenderText
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.component.Button
import dev.atlassoftware.libs.cipoflow.core.model.component.Image
import dev.atlassoftware.libs.cipoflow.core.model.component.ScreenComponent
import dev.atlassoftware.libs.cipoflow.core.model.component.Section
import dev.atlassoftware.libs.cipoflow.core.model.component.Spacer
import dev.atlassoftware.libs.cipoflow.core.model.component.Text

/**
 * The main renderer function. It takes a UiComponent and an action handler,
 * and delegates the rendering to specific component functions.
 *
 * @param component The UI component model to render.
 * @param onAction A lambda function to be invoked when a user action is triggered.
 */
@Composable
fun ComponentRenderer(component: ScreenComponent, onAction: (Action) -> Unit) {
    when (component) {
        is Text -> RenderText(component)
        is Button -> RenderButton(component, onAction)
        is Section -> RenderSection(component, onAction)
        is Spacer -> RenderSpacer(component)
        is Image -> RenderImage(component)
    }
}
