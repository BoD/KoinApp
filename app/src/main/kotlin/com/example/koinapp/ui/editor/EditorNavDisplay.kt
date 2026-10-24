@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.ui.editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.koinapp.navigation.EditorNavigator
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun EditorNavDisplay(editorNavigator: EditorNavigator) {
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
