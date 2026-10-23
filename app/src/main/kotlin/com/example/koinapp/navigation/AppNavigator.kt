package com.example.koinapp.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import java.util.UUID

class AppNavigator {
  val backStack = NavBackStack<NavKey>(Notes)

  fun startEditing() {
    backStack.add(Editor(UUID.randomUUID().toString()))
  }

  fun goBack() {
    if (backStack.size > 1) backStack.removeLastOrNull()
  }
}
