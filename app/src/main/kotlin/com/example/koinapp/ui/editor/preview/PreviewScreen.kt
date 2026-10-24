@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.ui.editor.preview

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.EditorNavigator
import com.example.koinapp.navigation.Preview
import com.example.koinapp.ui.components.Screen
import com.example.koinapp.ui.components.SessionLabel
import org.koin.compose.navigation3.annotation.NavigationEntry
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Scope
import org.koin.core.scope.Scope as KoinScope

@NavigationEntry
@Scope(EditingSession::class)
@Composable
fun KoinScope.PreviewDestination(route: Preview) {
  val editorNavigator = getSource<EditorNavigator>()!!
  val viewModel = koinViewModel<PreviewViewModel>()
  PreviewScreen(viewModel, onBack = editorNavigator::goBack, onDiscard = editorNavigator.onFinishEditor)
}

@Composable
private fun PreviewScreen(
  viewModel: PreviewViewModel,
  onBack: () -> Unit,
  onDiscard: () -> Unit
) {
  Screen(title = "Preview", onDiscard = onDiscard) {
    Text(
      text = viewModel.session.text.ifBlank { "Your draft is empty." },
      style = MaterialTheme.typography.bodyLarge,
    )
    Button(onClick = onBack) { Text("Back to draft") }
    SessionLabel(viewModel.session.sessionId)
  }
}
