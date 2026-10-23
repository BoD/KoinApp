package com.example.koinapp.ui.editor.draft

import androidx.lifecycle.ViewModel
import com.example.koinapp.editor.EditingSession

class DraftViewModel(val session: EditingSession) : ViewModel() {
  fun updateText(text: String) {
    session.text = text
  }
}
