package de.stefanoberdoerfer.bierumvier

import android.app.Application

class BeerApplication : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: BeerApplication
            private set
    }
}