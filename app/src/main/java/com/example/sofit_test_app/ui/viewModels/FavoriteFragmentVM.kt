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
import com.example.sofit_test_app.ui.adapters.DrinksAdapter
import kotlinx.coroutines.launch

class FavoriteFragmentVM(context: Context) : ViewModel() {

    private val drinkRepo = DrinkRepo(context)

    private val _drinks = MutableLiveData<List<Drink>>()
    val drinks: LiveData<List<Drink>> get() = _drinks

    var isListVisible = ObservableField(false)

    val favoriteRecipesAdapter = DrinksAdapter { drink, action ->
        when (action) {
            0 -> setDrink(
                convertModels(drink)
            )

            1 -> deleteDrink(
                convertModels(drink)
            )
        }
        getAllDrinks()
    }

    fun setAdapter(context: Context) {
        isListVisible.set(true)
        favoriteRecipesAdapter.setData(drinks.value!!, context)
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

    fun getAllDrinks() {
        viewModelScope.launch {
            drinkRepo.getAllDrinks().collect {
                _drinks.value = it.map { drinkModel ->
                    Drink(
                        idDrink = drinkModel.idDrink.toString(),
                        strDrink = drinkModel.strDrink!!,
                        strDrinkThumb = drinkModel.strDrinkThumb!!,
                        strInstructions = drinkModel.strInstructions!!,
                        strAlcoholic = drinkModel.strAlcoholic!!,
                        isFavorite = true
                    )
                }
            }
        }
    }
}
