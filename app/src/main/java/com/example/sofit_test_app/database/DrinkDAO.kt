package com.example.sofit_test_app.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface DrinkDAO {

    @Insert(onConflict = REPLACE)
    fun insertDrink(drink: DrinkModel)

    @Query(value = "SELECT * FROM drink_table WHERE idDrink = :id")
    fun getDrinkById(id: Int): DrinkModel

    @Query(value = "SELECT * FROM drink_table")
    fun getAllDrinks(): Flow<List<DrinkModel>>

    @Update
    fun updateDrink(drink: DrinkModel)

    @Delete
    fun deleteDrink(drink: DrinkModel)

}