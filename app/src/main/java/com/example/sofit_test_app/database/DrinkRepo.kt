package com.example.sofit_test_app.database

import androidx.room.Room

class DrinkRepo(context: android.content.Context) {
    private val database: DrinkDatabase = Room.databaseBuilder(
        context.applicationContext, DrinkDatabase::class.java, "database-name"
    ).allowMainThreadQueries().build()

    fun insert(drink: DrinkModel) = database.drinkDao().insertDrink(drink)
    fun update(drink: DrinkModel) = database.drinkDao().updateDrink(drink)
    fun delete(drink: DrinkModel) = database.drinkDao().deleteDrink(drink)
    fun getAllDrinks() = database.drinkDao().getAllDrinks()
    fun getDrinkById(id: Int) = database.drinkDao().getDrinkById(id)

}


