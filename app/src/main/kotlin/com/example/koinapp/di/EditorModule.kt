@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.di

import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.Draft
import com.example.koinapp.navigation.EditorNavigator
import com.example.koinapp.navigation.Preview
import com.example.koinapp.ui.editor.draft.DraftScreen
import com.example.koinapp.ui.editor.draft.DraftViewModel
import com.example.koinapp.ui.editor.preview.PreviewScreen
import com.example.koinapp.ui.editor.preview.PreviewViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.dsl.onClose
import org.koin.plugin.module.dsl.scoped

val editorModule = module {
  scope<EditingSession> {
    scoped<EditingSession>().onClose { it?.close() }

    viewModel { DraftViewModel(get()) }

    viewModel { PreviewViewModel(get()) }

    navigation<Draft> {
      val editorNavigator = getSource<EditorNavigator>()!!
      val viewModel = koinViewModel<DraftViewModel>(scope = this)
      DraftScreen(viewModel, onPreview = editorNavigator::preview, onDiscard = editorNavigator.onFinishEditor)
    }

    navigation<Preview> {
      val editorNavigator = getSource<EditorNavigator>()!!
      val viewModel = koinViewModel<PreviewViewModel>(scope = this)
      PreviewScreen(viewModel, onBack = editorNavigator::goBack, onDiscard = editorNavigator.onFinishEditor)
    }
  }
}
