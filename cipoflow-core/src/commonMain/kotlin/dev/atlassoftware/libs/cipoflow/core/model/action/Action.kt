package dev.atlassoftware.libs.cipoflow.core.model.action


sealed class Action {
    data class Navigate(val screenId: String) : Action()
    data class ShowToast(val message: String) : Action()
    data class InputChanged(val id: String, val value: String) : Action()
    object GoBack : Action()
}