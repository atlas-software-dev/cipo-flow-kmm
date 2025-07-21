package org.example.poke

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.atlassoftware.libs.cipoflow.compose.config.CipoFlowComposeApp
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.dsl.screen

@Composable
fun App() {
    MaterialTheme {
        CipoFlowComposeApp {
            screens(
                screen("home") {
                    title = "Home Screen"
                    content {
                        section("welcome_section") {
                            title = "Welcome!"
                            items {
                                text("intro_text", "This UI is defined with the low-code DSL.")
                                spacer("spacer1", 16)
                                button("profile_button") {
                                    text = "Go to Profile"
                                    onClick(Action.Navigate("profile"))
                                }
                                button("settings_button") {
                                    text = "Show a Toast"
                                    onClick(Action.ShowToast("Hello from the DSL!"))
                                }
                            }
                        }
                    }
                },
                screen("profile") {
                    title = "Profile"
                    content {
                        text("name", "User: Alex Ray")
                        text("role", "Role: Developer")
                        spacer("spacer2", 20)
                        button("back_button") {
                            text = "Go Back"
                            onClick(Action.GoBack)
                        }
                    }
                }
            )
            startScreenId = "home"
        }
    }
}
