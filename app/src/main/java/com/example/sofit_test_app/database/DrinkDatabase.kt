package com.example.sofit_test_app.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DrinkModel::class], version = 1, exportSchema = true)
abstract class DrinkDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDAO
}