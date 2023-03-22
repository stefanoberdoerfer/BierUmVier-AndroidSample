package de.stefanoberdoerfer.bierumvier

import android.app.Application
import de.stefanoberdoerfer.bierumvier.di.appModule
import de.stefanoberdoerfer.bierumvier.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BeerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@BeerApplication)
            // Load modules
            modules(listOf(appModule, viewModelModules))
        }
    }
}