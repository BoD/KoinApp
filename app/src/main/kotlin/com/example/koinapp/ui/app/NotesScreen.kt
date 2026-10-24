@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.ui.app

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.koinapp.navigation.AppNavigator
import com.example.koinapp.navigation.Notes
import com.example.koinapp.ui.components.Screen
import org.koin.android.annotation.ActivityRetainedScope
import org.koin.compose.navigation3.annotation.NavigationEntry
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.scope.Scope

@NavigationEntry
@ActivityRetainedScope
@Composable
fun Scope.NotesDestination(route: Notes) {
  NotesScreen(onNewDraft = get<AppNavigator>()::startEditing)
}

@Composable
private fun NotesScreen(
  onNewDraft: () -> Unit,
) {
  Screen(title = "Notes") {
    Text("Write a draft and preview it.")
    Button(onClick = onNewDraft) { Text("New draft") }
  }
}
