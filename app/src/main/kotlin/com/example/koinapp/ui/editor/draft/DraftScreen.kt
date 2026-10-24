package com.example.koinapp.ui.editor.draft

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.koinapp.ui.components.Screen
import com.example.koinapp.ui.components.SessionLabel

@Composable
fun DraftScreen(
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
