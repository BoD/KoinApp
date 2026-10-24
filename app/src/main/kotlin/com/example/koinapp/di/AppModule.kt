@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.di

import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.AppNavigator
import com.example.koinapp.navigation.Editor
import com.example.koinapp.navigation.EditorNavigator
import com.example.koinapp.navigation.Notes
import com.example.koinapp.ui.app.NotesScreen
import com.example.koinapp.ui.editor.EditorNavDisplay
import org.koin.androidx.scope.dsl.activityRetainedScope
import org.koin.compose.navigation3.KoinNavEntryScope
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.scoped

val appModule = module {
  activityRetainedScope {
    scoped<AppNavigator>()

    navigation<Notes> {
      NotesScreen(onNewDraft = get<AppNavigator>()::startEditing)
    }

    navigation<Editor> { route ->
      val appNavigator = get<AppNavigator>()
      KoinNavEntryScope<EditingSession>(
        scopeID = "editing:${route.sessionId}",
        source = { EditorNavigator(appNavigator::goBack) },
      ) {
        EditorNavDisplay(editorNavigator = getSource<EditorNavigator>()!!)
      }
    }
  }
}
