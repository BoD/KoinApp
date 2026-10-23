package com.example.koinapp.ui.editor

import androidx.lifecycle.ViewModel
import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.EditorNavigator
import org.koin.core.Koin
import org.koin.core.scope.Scope

class EditorViewModel(
  sessionId: String,
  koin: Koin,
  onFinishEditor: () -> Unit,
) : ViewModel() {
  val editorNavigator = EditorNavigator(sessionId, onFinishEditor)
  val scope: Scope = koin.createScope<EditingSession>(
    scopeId = "editing:$sessionId",
    source = editorNavigator,
  )

  override fun onCleared() {
    scope.close()
  }
}
