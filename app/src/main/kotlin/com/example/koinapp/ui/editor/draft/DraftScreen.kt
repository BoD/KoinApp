@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.ui.editor.draft

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.Draft
import com.example.koinapp.navigation.EditorNavigator
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
fun KoinScope.DraftDestination(route: Draft) {
  val editorNavigator = getSource<EditorNavigator>()!!
  val viewModel = koinViewModel<DraftViewModel>()
  DraftScreen(viewModel, onPreview = editorNavigator::preview, onDiscard = editorNavigator.onFinishEditor)
}

@Composable
private fun DraftScreen(
  viewModel: DraftViewModel,
  onPreview: () -> Unit,
  onDiscard: () -> Unit
) {
  Screen(title = "Edit draft", onDiscard = onDiscard) {
    OutlinedTextField(
      value = viewModel.session.text,
      onValueChange = viewModel::updateText,
      label = { Text("Your draft") },
      minLines = 8,
      modifier = Modifier.fillMaxWidth(),
    )
    Button(onClick = onPreview) { Text("Preview draft") }
    SessionLabel(viewModel.session.sessionId)
  }
}
