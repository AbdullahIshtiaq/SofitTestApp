package com.example.sofit_test_app.utils

import com.example.sofit_test_app.model.Drink
import com.example.sofit_test_app.model.DrinkResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class APIRepo {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val drinkService: DrinkService = retrofit.create(DrinkService::class.java)


    suspend fun searchDrinksByName(name: String): List<Drink> {
        val response = drinkService.searchByName(name)
        return response.drinks
    }

    suspend fun searchDrinksByAlphabet(alphabet: String): List<Drink> {
        val response = drinkService.searchByAlphabet(alphabet)
        return response.drinks
    }
}


//interface DrinkService {
//    @GET("search.php?s={name}")
//    suspend fun searchByName(@Path("name") name: String): DrinkResponse
//
//    @GET("search.php?f={alphabet}")
//    suspend fun searchByAlphabet(@Path("alphabet") alphabet: String): DrinkResponse
//
//}

interface DrinkService {
    @GET("search.php")
    suspend fun searchByName(@Query("s") name: String): DrinkResponse

    @GET("search.php")
    suspend fun searchByAlphabet(@Query("f") alphabet: String): DrinkResponse
}
