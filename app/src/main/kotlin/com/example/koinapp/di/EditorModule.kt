package com.example.koinapp.di

import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.EditorNavigator
import com.example.koinapp.ui.editor.draft.DraftViewModel
import com.example.koinapp.ui.editor.preview.PreviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.onClose

val editorModule = module {
  scope<EditingSession> {
    scoped { EditingSession(getSource<EditorNavigator>()!!.sessionId) }.onClose { it?.close() }

    viewModel { DraftViewModel(get()) }

    viewModel { PreviewViewModel(get()) }
  }
}
