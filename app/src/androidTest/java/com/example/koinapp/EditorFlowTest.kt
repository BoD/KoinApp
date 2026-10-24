package com.example.koinapp

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.pressBack
import com.example.koinapp.editor.EditingSession
import com.example.koinapp.navigation.AppNavigator
import com.example.koinapp.navigation.Editor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test

class EditorFlowTest {
  @get:Rule
  val compose = createAndroidComposeRule<MainActivity>()

  @Test
  fun sharedDraftSurvivesPreviewAndRecreationAndDiscardStartsFresh() {
    compose.onNodeWithText("New draft").performClick()
    val originalSession = sessionLabel()
    compose.onNodeWithText("Your draft").performTextInput("My first note")
    compose.onNodeWithText("Preview draft").performScrollTo().performClick()
    compose.onNodeWithText("My first note").assertIsDisplayed()
    assertEquals(originalSession, sessionLabel())

    compose.activityRule.scenario.recreate()
    compose.onNodeWithText("My first note").assertIsDisplayed()
    assertEquals(originalSession, sessionLabel())

    compose.onNodeWithText("Back to draft").performClick()
    compose.onNodeWithText("Your draft").assertTextContains("My first note")
    compose.onNodeWithText("Discard draft").performClick()
    compose.onNodeWithText("New draft").performClick()
    assertNotEquals(originalSession, sessionLabel())
    val text = compose.onNodeWithText("Your draft").fetchSemanticsNode()
      .config[SemanticsProperties.EditableText].text
    assertEquals("", text)
  }

  private fun sessionLabel(): String = compose.onNodeWithTag("editing-session")
    .performScrollTo()
    .fetchSemanticsNode().config[SemanticsProperties.Text].single().text

  @Test
  fun androidBackReturnsToDraftThenClosesTheEditingScope() {
    compose.onNodeWithText("New draft").performClick()
    compose.onNodeWithText("Preview draft").performClick()
    val session = compose.runOnIdle {
      val route = compose.activity.scope.get<AppNavigator>().backStack.last() as Editor
      compose.activity.scope.getKoin().getScope("editing:${route.sessionId}")
        .get<EditingSession>()
    }

    pressBack()
    compose.onNodeWithText("Edit draft").assertIsDisplayed()
    compose.runOnIdle { org.junit.Assert.assertFalse(session.isClosed) }

    pressBack()
    compose.onNodeWithText("Notes").assertIsDisplayed()
    compose.waitUntil { session.isClosed }
  }

  @Test
  fun discardingFromPreviewClosesTheEntireEditingFlow() {
    compose.onNodeWithText("New draft").performClick()
    compose.onNodeWithText("Preview draft").performClick()
    val scope = compose.runOnIdle {
      val route = compose.activity.scope.get<AppNavigator>().backStack.last() as Editor
      compose.activity.scope.getKoin().getScope("editing:${route.sessionId}")
    }
    val session = compose.runOnIdle { scope.get<EditingSession>() }

    compose.onNodeWithText("Discard draft").performClick()
    compose.onNodeWithText("Notes").assertIsDisplayed()
    compose.waitUntil { session.isClosed }
    compose.runOnIdle {
      org.junit.Assert.assertNull(
        compose.activity.scope.getKoin().getScopeOrNull(scope.id),
      )
    }
  }
}
