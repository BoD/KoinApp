package com.example.koinapp.ui.editor.preview

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.koinapp.ui.components.Screen
import com.example.koinapp.ui.components.SessionLabel

@Composable
fun PreviewScreen(
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
    SessionLabel(viewModel.session.instanceId)
  }
}
