@file:OptIn(KoinExperimentalAPI::class)

package com.example.koinapp.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.koinapp.navigation.AppNavigator
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.scope.Scope

@Composable
fun AppNavDisplay(appNavigator: AppNavigator, scope: Scope) {
  NavDisplay(
    modifier = Modifier.fillMaxSize(),
    backStack = appNavigator.backStack,
    onBack = appNavigator::goBack,
    entryDecorators = listOf(
      rememberSaveableStateHolderNavEntryDecorator(),
      rememberViewModelStoreNavEntryDecorator(),
    ),
    entryProvider = koinEntryProvider(scope = scope),
  )
}
