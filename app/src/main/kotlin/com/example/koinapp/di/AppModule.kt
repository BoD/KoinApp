package com.example.koinapp.di

import com.example.koinapp.navigation.AppNavigator
import org.koin.androidx.scope.dsl.activityRetainedScope
import org.koin.dsl.module
import org.koin.plugin.module.dsl.scoped

val appModule = module {
  activityRetainedScope {
    scoped<AppNavigator>()
  }
}
