package dev.atlassoftware.libs.cipoflow.core.model.component

enum class ScaleType {
    Fit, FillBounds, CenterCrop, CenterInside, None
}

enum class ImageAlignment {
    TopStart, TopCenter, TopEnd,
    CenterStart, Center, CenterEnd,
    BottomStart, BottomCenter, BottomEnd
}

data class Image(
    override val id: String,
    val url: String,
    val contentDescription: String? = null,
    val scaleType: ScaleType = ScaleType.Fit,
    val cornerRadius: Int = 0,
    val width: Int? = null,
    val height: Int? = null,
    val alignment: ImageAlignment = ImageAlignment.Center
) : ScreenComponent
