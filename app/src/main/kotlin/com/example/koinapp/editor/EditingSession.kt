package com.example.koinapp.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.UUID

class EditingSession {
  val instanceId: String = UUID.randomUUID().toString()
  var text: String by mutableStateOf("")
  var isClosed: Boolean = false
    private set

  init {
    println("EditingSession created: $instanceId")
  }

  fun close() {
    isClosed = true
    println("EditingSession closed: $instanceId")
  }
}
