package com.example.sofit_test_app.model

import com.google.gson.annotations.SerializedName

data class DrinkResponse(
    @SerializedName("drinks") val drinks: List<Drink>
)

data class Drink(
    @SerializedName("idDrink") val idDrink: String,
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String,
    @SerializedName("strAlcoholic") val strAlcoholic: String,
    @SerializedName("strInstructions") val strInstructions: String,
    var isFavorite: Boolean = false
)