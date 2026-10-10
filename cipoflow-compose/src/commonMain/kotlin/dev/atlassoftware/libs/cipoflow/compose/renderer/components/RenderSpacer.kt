package dev.atlassoftware.libs.cipoflow.compose.renderer.components

import androidx.compose.foundation.layout.Spacer as ComposeSpacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.atlassoftware.libs.cipoflow.core.model.component.Spacer

@Composable
fun RenderSpacer(component: Spacer, modifier: Modifier = Modifier) {
    ComposeSpacer(modifier = modifier.height(component.height.dp))
}
