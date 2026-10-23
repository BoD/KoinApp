package com.example.koinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.koinapp.navigation.AppNavigator
import com.example.koinapp.ui.app.AppNavDisplay
import com.example.koinapp.ui.theme.KoinAppTheme
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.core.scope.Scope

class MainActivity : ComponentActivity(), AndroidScopeComponent {
  override val scope: Scope by activityRetainedScope()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    val appNavigator = scope.get<AppNavigator>()
    setContent {
      KoinAppTheme {
        AppNavDisplay(appNavigator, scope)
      }
    }
  }
}
