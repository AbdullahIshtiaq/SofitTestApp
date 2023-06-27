package com.example.sofit_test_app.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun getLastSearchByName(): String {
        return sharedPreferences.getString("ByName", "") ?: ""
    }

    fun saveLastSearchByName(query: String) {
        sharedPreferences.edit().putString("ByName", query).apply()
    }

    fun getLastSearchByAlphabet(): String {
        return sharedPreferences.getString("ByAlphabet", "") ?: ""
    }

    fun saveLastSearchByAlphabet(query: String) {
        sharedPreferences.edit().putString("ByAlphabet", query).apply()
    }
}