package com.example.sofit_test_app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sofit_test_app.R
import com.example.sofit_test_app.databinding.LayoutDrinkItemBinding
import com.example.sofit_test_app.model.Drink

class DrinksAdapter(val adapterCallback: (Drink, Int) -> Unit) :
    RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>() {

    private var drinkList: List<Drink> = listOf()
    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_drink_item, parent, false)
        return DrinkViewHolder(view)

    }

    fun setData(data: List<Drink>, context: Context) {
        this.context = context
        drinkList = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bindLayout(drinkList[position])
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    inner class DrinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = LayoutDrinkItemBinding.bind(view)

        fun bindLayout(drink: Drink) {

            binding.drinkTitleTextView.text = drink.strDrink
            binding.drinkDescriptionTextView.text = drink.strInstructions

            Glide.with(binding.drinkImageView.context)
                .load(drink.strDrinkThumb)
                .centerCrop()
                .placeholder(R.drawable.ic_broken)
                .into(binding.drinkImageView);

            binding.drinkCheckBox.isChecked = (drink.strAlcoholic == "Alcoholic")
            if (drink.isFavorite) {
                binding.drinkFavouriteImageView.setImageResource(R.drawable.ic_star_fill)
            }

            binding.drinkFavouriteImageView.setOnClickListener {
                if (drink.isFavorite) {
                    drink.isFavorite = false
                    binding.drinkFavouriteImageView.setImageResource(R.drawable.ic_star_outline)
                    adapterCallback(drink, 1)
                } else {
                    drink.isFavorite = true
                    binding.drinkFavouriteImageView.setImageResource(R.drawable.ic_star_fill)
                    adapterCallback(drink, 0)
                }
            }

        }

    }
}