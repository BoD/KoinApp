package com.example.koinapp

import androidx.lifecycle.ViewModelStore
import com.example.koinapp.di.editorModule
import com.example.koinapp.editor.EditingSession
import com.example.koinapp.ui.editor.EditorViewModel
import com.example.koinapp.ui.editor.draft.DraftViewModel
import com.example.koinapp.ui.editor.preview.PreviewViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import org.koin.dsl.koinApplication

class EditorScopeTest {
  @Test
  fun draftAndPreviewShareSessionUntilFlowOwnerIsCleared() {
    val application = koinApplication { modules(editorModule) }
    val store = ViewModelStore()
    try {
      val owner = EditorViewModel("first", application.koin) {}
      store.put("editor", owner)
      val draft = owner.scope.get<DraftViewModel>()
      val preview = owner.scope.get<PreviewViewModel>()

      draft.updateText("A shared draft")
      assertSame(draft.session, preview.session)
      assertEquals("A shared draft", preview.session.text)

      owner.editorNavigator.preview()
      owner.editorNavigator.goBack()
      assertFalse(owner.scope.closed)
      assertFalse(draft.session.isClosed)

      store.clear()
      assertTrue(owner.scope.closed)
      assertTrue(draft.session.isClosed)
    } finally {
      store.clear()
      application.close()
    }
  }

  @Test
  fun independentFlowsHaveIndependentSessions() {
    val application = koinApplication { modules(editorModule) }
    val store = ViewModelStore()
    try {
      val first = EditorViewModel("first", application.koin) {}
      val second = EditorViewModel("second", application.koin) {}
      store.put("first", first)
      store.put("second", second)
      val firstSession = first.scope.get<EditingSession>()
      val secondSession = second.scope.get<EditingSession>()

      firstSession.text = "First flow only"
      assertNotSame(firstSession, secondSession)
      assertEquals("", secondSession.text)
    } finally {
      store.clear()
      application.close()
    }
  }
}
