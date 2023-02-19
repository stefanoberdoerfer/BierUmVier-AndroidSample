package de.stefanoberdoerfer.bierumvier.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.stefanoberdoerfer.bierumvier.BeerApplication
import de.stefanoberdoerfer.bierumvier.data.db.model.BeerEntity

@Database(
    entities = [
        BeerEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
}

object AppDb {
    val instance by lazy {
        Room.databaseBuilder(BeerApplication.instance, AppDatabase::class.java, "beer_db")
            .build()
    }
}