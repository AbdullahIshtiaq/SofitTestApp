package com.example.sofit_test_app.ui.viewModels

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofit_test_app.database.DrinkModel
import com.example.sofit_test_app.database.DrinkRepo
import com.example.sofit_test_app.model.Drink
import com.example.sofit_test_app.utils.APIRepo
import com.example.sofit_test_app.ui.adapters.DrinksAdapter
import kotlinx.coroutines.launch


class HomeFragmentVM(context: Context) : ViewModel() {

    private val drinkRepo = DrinkRepo(context)
    private val apiRepo = APIRepo()
    private val _drinks = MutableLiveData<List<Drink>>()
    val drinks: LiveData<List<Drink>> get() = _drinks

    private val _drinksFavorite = MutableLiveData<List<DrinkModel>>()
    private val drinksFavorite: LiveData<List<DrinkModel>> get() = _drinksFavorite

    var isListVisible = ObservableField(false)

    init {
        getAllDrinks()
    }

    val drinksAdapter = DrinksAdapter { drink, action ->
        when (action) {
            0 -> setDrink(
                convertModels(drink)
            )

            1 -> deleteDrink(
                convertModels(drink)
            )
        }
    }

    fun setAdapter(context: Context) {
        filterFavoriteDrinks()
        isListVisible.set(true)
        drinksAdapter.setData(drinks.value!!, context)
    }

    private fun convertModels(drink: Drink): DrinkModel {
        return DrinkModel(
            idDrink = drink.idDrink.toInt(),
            strDrink = drink.strDrink,
            strDrinkThumb = drink.strDrinkThumb,
            strInstructions = drink.strInstructions,
            strAlcoholic = drink.strAlcoholic
        )
    }

    fun searchByName(searchQuery: String) {
        viewModelScope.launch {
            val result = apiRepo.searchDrinksByName(searchQuery)
            _drinks.value = result
        }
    }

    fun searchByFirstAlphabet(searchQuery: String) {
        viewModelScope.launch {
            val result = apiRepo.searchDrinksByAlphabet(searchQuery)
            _drinks.value = result
        }
    }

    private fun setDrink(drinks: DrinkModel) {
        viewModelScope.launch {
            drinkRepo.insert(drinks)
        }
    }

    private fun deleteDrink(drinks: DrinkModel) {
        viewModelScope.launch {
            drinkRepo.delete(drinks)
        }
    }

    private fun getAllDrinks() {
        viewModelScope.launch {
            drinkRepo.getAllDrinks().collect {
                _drinksFavorite.value = it
            }
        }
    }

    private fun filterFavoriteDrinks() {

        drinks.value?.forEach {
            drinksFavorite.value?.find { drinkModel ->
                drinkModel.idDrink == it.idDrink.toInt()
            }.let { drinkModel ->
                it.isFavorite = drinkModel != null
            }
        }

    }
}

