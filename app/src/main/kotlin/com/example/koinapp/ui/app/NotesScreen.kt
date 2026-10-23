package com.example.koinapp.ui.app

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.koinapp.ui.components.Screen

@Composable
fun NotesScreen(
  onNewDraft: () -> Unit,
) {
  Screen(title = "Notes") {
    Text("Write a draft and preview it.")
    Button(onClick = onNewDraft) { Text("New draft") }
  }
}
