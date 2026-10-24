package com.example.koinapp

import com.example.koinapp.di.editorModule
import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.EditorNavigator
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
  fun draftAndPreviewShareSessionUntilScopeCloses() {
    val application = koinApplication { modules(editorModule) }
    try {
      val navigator = EditorNavigator {}
      val scope = application.koin.createScope<EditingSession>("first", source = navigator)
      val draft = scope.get<DraftViewModel>()
      val preview = scope.get<PreviewViewModel>()

      draft.updateText("A shared draft")
      assertSame(draft.session, preview.session)
      assertEquals("A shared draft", preview.session.text)

      navigator.preview()
      navigator.goBack()
      assertFalse(scope.closed)
      assertFalse(draft.session.isClosed)

      scope.close()
      assertTrue(scope.closed)
      assertTrue(draft.session.isClosed)
    } finally {
      application.close()
    }
  }

  @Test
  fun independentFlowsHaveIndependentSessions() {
    val application = koinApplication { modules(editorModule) }
    try {
      val first = application.koin.createScope<EditingSession>("first")
      val second = application.koin.createScope<EditingSession>("second")
      val firstSession = first.get<EditingSession>()
      val secondSession = second.get<EditingSession>()

      firstSession.text = "First flow only"
      assertNotSame(firstSession, secondSession)
      assertEquals("", secondSession.text)
    } finally {
      application.close()
    }
  }
}
