@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.ui.editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.AppNavigator
import com.example.koinapp.navigation.Editor
import com.example.koinapp.navigation.EditorNavigator
import org.koin.android.annotation.ActivityRetainedScope
import org.koin.compose.navigation3.KoinNavEntryScope
import org.koin.compose.navigation3.annotation.NavigationEntry
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.scope.Scope

@NavigationEntry
@ActivityRetainedScope
@Composable
fun Scope.EditorDestination(route: Editor) {
  val appNavigator = get<AppNavigator>()
  KoinNavEntryScope<EditingSession>(
    scopeID = "editing:${route.sessionId}",
    source = { EditorNavigator(route.sessionId, appNavigator::goBack) },
  ) {
    EditorNavDisplay(editorNavigator = getSource<EditorNavigator>()!!)
  }
}

@Composable
private fun EditorNavDisplay(editorNavigator: EditorNavigator) {
  NavDisplay(
    modifier = Modifier.fillMaxSize(),
    backStack = editorNavigator.backStack,
    onBack = editorNavigator::goBack,
    entryDecorators = listOf(
      rememberSaveableStateHolderNavEntryDecorator(),
      rememberViewModelStoreNavEntryDecorator(),
    ),
    entryProvider = koinEntryProvider(),
  )
}
