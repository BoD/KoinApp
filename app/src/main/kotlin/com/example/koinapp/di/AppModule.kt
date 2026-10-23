@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.di

import com.example.koinapp.navigation.AppNavigator
import com.example.koinapp.navigation.Editor
import com.example.koinapp.navigation.Notes
import com.example.koinapp.ui.app.NotesScreen
import com.example.koinapp.ui.editor.EditorNavDisplay
import com.example.koinapp.ui.editor.EditorViewModel
import org.koin.androidx.scope.dsl.activityRetainedScope
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.scoped

val appModule = module {
  activityRetainedScope {
    scoped<AppNavigator>()

    viewModel { (sessionId: String) ->
      EditorViewModel(sessionId = sessionId, koin = getKoin(), onFinishEditor = get<AppNavigator>()::goBack)
    }

    navigation<Notes> {
      NotesScreen(onNewDraft = get<AppNavigator>()::startEditing)
    }

    navigation<Editor> { route ->
      val owner = koinViewModel<EditorViewModel>(scope = this) {
        parametersOf(route.sessionId)
      }
      EditorNavDisplay(owner)
    }
  }
}
