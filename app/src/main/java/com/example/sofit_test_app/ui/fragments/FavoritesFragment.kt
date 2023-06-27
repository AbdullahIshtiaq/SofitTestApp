package com.example.sofit_test_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofit_test_app.R
import com.example.sofit_test_app.databinding.FragmentFavoritesBinding
import com.example.sofit_test_app.ui.viewModels.FavoriteFragmentVM


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    private lateinit var viewModel: FavoriteFragmentVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        viewModel = FavoriteFragmentVM(requireContext())
        binding = FragmentFavoritesBinding.bind(view)
        binding.viewModel = viewModel
        binding.favoriteRecipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getAllDrinks()
        observeData()

        return view
    }


    private fun observeData() {
        viewModel.drinks.observe(viewLifecycleOwner) { drinks ->
            if (!drinks.isNullOrEmpty()) {
                viewModel.setAdapter(requireContext())
            }
        }
    }
}