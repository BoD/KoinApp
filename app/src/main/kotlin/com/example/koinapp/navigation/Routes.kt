package com.example.koinapp.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// App

@Serializable
data object Notes : NavKey

@Serializable
data class Editor(val sessionId: String) : NavKey


// Editor

@Serializable
data object Draft : NavKey


@Serializable
data object Preview : NavKey
