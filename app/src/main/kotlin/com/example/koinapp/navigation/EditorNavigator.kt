package com.example.koinapp.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class EditorNavigator(val sessionId: String, val onFinishEditor: () -> Unit) {
  val backStack = NavBackStack<NavKey>(Draft)

  fun preview() {
    if (backStack.lastOrNull() == Draft) backStack.add(Preview)
  }

  fun goBack() {
    if (backStack.size > 1) backStack.removeLastOrNull()
  }
}
