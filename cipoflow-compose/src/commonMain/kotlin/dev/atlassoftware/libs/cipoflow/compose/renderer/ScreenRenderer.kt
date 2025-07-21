package dev.atlassoftware.libs.cipoflow.compose.renderer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.atlassoftware.libs.cipoflow.core.model.action.Action
import dev.atlassoftware.libs.cipoflow.core.model.screen.ScreenDefinition

object ScreenRenderer {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun render(
        screenDefinition: ScreenDefinition,
        showBackButton: Boolean,
        actionDelegate: (Action) -> Unit,
        snackbarHostState: SnackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(screenDefinition.title) },
                    navigationIcon = {
                        if (showBackButton) {
                            IconButton(onClick = { actionDelegate(Action.GoBack) }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        } else {
                            null
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Make screen scrollable for long content
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                screenDefinition.content.forEach { component ->
                    ComponentRenderer(component, actionDelegate)
                }
            }
        }
    }
}