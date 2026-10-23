package com.example.koinapp

import android.app.Application
import com.example.koinapp.di.appModule
import com.example.koinapp.di.editorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinExampleApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@KoinExampleApplication)
      modules(appModule, editorModule)
    }
  }
}
