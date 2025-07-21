package dev.atlassoftware.libs.cipoflow.core.model.screen
import dev.atlassoftware.libs.cipoflow.core.model.component.ScreenComponent


sealed interface Screen {
    val id: String
    val content: List<ScreenComponent>
}

