package dev.atlassoftware.libs.cipoflow.core.model.action


sealed class Action {
    data class Navigate(val screenId: String) : Action()
    data class ShowToast(val message: String) : Action()
    object GoBack : Action()
}