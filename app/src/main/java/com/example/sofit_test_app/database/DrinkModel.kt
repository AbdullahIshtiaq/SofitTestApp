package com.example.sofit_test_app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "drink_table")
data class DrinkModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idDrink")
    var idDrink: Int = 0,
    @ColumnInfo(name = "strDrink")
    var strDrink: String? = null,
    @ColumnInfo(name = "strDrinkThumb")
    var strDrinkThumb: String? = null,
    @ColumnInfo(name = "strAlcoholic")
    var strAlcoholic: String? = null,
    @ColumnInfo(name = "strInstructions")
    var strInstructions: String? = null,
) : Serializable