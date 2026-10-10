package dev.atlassoftware.libs.cipoflow.compose.renderer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.atlassoftware.libs.cipoflow.core.model.component.Image
import dev.atlassoftware.libs.cipoflow.core.model.component.ImageAlignment
import dev.atlassoftware.libs.cipoflow.core.model.component.ScaleType

@Composable
fun RenderImage(component: Image, modifier: Modifier = Modifier) {
    val imageModifier = modifier
        .padding(vertical = 8.dp)
        .then(
            if (component.width != null && component.height != null) {
                Modifier.size(component.width!!.dp, component.height!!.dp)
            } else if (component.width != null) {
                Modifier.width(component.width!!.dp)
            } else if (component.height != null) {
                Modifier.height(component.height!!.dp)
            } else {
                Modifier.fillMaxWidth()
            }
        )
        .clip(RoundedCornerShape(component.cornerRadius.dp))

    AsyncImage(
        model = component.url,
        contentDescription = component.contentDescription,
        modifier = imageModifier,
        contentScale = component.scaleType.toComposeContentScale(),
        alignment = component.alignment.toComposeAlignment()
    )
}

private fun ScaleType.toComposeContentScale(): ContentScale = when (this) {
    ScaleType.Fit -> ContentScale.Fit
    ScaleType.FillBounds -> ContentScale.FillBounds
    ScaleType.CenterCrop -> ContentScale.Crop
    ScaleType.CenterInside -> ContentScale.Inside
    ScaleType.None -> ContentScale.None
}

private fun ImageAlignment.toComposeAlignment(): Alignment = when (this) {
    ImageAlignment.TopStart -> Alignment.TopStart
    ImageAlignment.TopCenter -> Alignment.TopCenter
    ImageAlignment.TopEnd -> Alignment.TopEnd
    ImageAlignment.CenterStart -> Alignment.CenterStart
    ImageAlignment.Center -> Alignment.Center
    ImageAlignment.CenterEnd -> Alignment.CenterEnd
    ImageAlignment.BottomStart -> Alignment.BottomStart
    ImageAlignment.BottomCenter -> Alignment.BottomCenter
    ImageAlignment.BottomEnd -> Alignment.BottomEnd
}
