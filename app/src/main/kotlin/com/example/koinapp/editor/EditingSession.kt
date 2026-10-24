package com.example.koinapp.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class EditingSession(val sessionId: String) {
  var text: String by mutableStateOf("")
  var isClosed: Boolean = false
    private set

  init {
    println("EditingSession created: $sessionId")
  }

  fun close() {
    isClosed = true
    println("EditingSession closed: $sessionId")
  }
}
