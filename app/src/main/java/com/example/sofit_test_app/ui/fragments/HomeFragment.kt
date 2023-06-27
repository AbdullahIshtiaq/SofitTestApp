package com.example.sofit_test_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofit_test_app.R
import com.example.sofit_test_app.databinding.FragmentHomeBinding
import com.example.sofit_test_app.utils.NetworkUtils.isInternetAvailable
import com.example.sofit_test_app.utils.SharedPrefs
import com.example.sofit_test_app.ui.viewModels.HomeFragmentVM


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeFragmentVM

    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel = HomeFragmentVM(requireContext())
        binding = FragmentHomeBinding.bind(view)
        binding.viewModel = viewModel
        binding.drinkRecipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        sharedPrefs = SharedPrefs(requireContext())

        initializeViews()
        observeData()

        return view
    }

    private fun initializeViews() {
        binding.apply {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radioButton_ByName -> {
                        radioButtonByName.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                        radioButtonByFirstAlphabet.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.grey
                            )
                        )
                        sharedPrefs.getLastSearchByName().let {
                            searchText.queryHint = it
                        }

                    }

                    R.id.radioButton_ByFirstAlphabet -> {
                        radioButtonByName.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.grey
                            )
                        )
                        radioButtonByFirstAlphabet.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )

                        sharedPrefs.getLastSearchByAlphabet().let {
                            searchText.queryHint = it
                        }
                    }
                }
            }

            searchText.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        val searchQuery = searchText.query.toString().trim()
                        val isInternetAvailable = isInternetAvailable(requireContext())
                        if (!isInternetAvailable) {
                            Toast.makeText(
                                requireContext(),
                                "No internet connection",
                                Toast.LENGTH_SHORT
                            ).show()
                            return false
                        }
                        if (searchQuery.isNotEmpty()) {
                            if (radioButtonByName.isChecked) {
                                sharedPrefs.saveLastSearchByName(searchQuery)
                                viewModel!!.searchByName(searchQuery)
                            } else if (radioButtonByFirstAlphabet.isChecked) {
                                sharedPrefs.saveLastSearchByAlphabet(searchQuery)
                                viewModel!!.searchByFirstAlphabet(searchQuery.first().toString())
                            }
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                }
            )
        }

    }

    private fun observeData() {
        viewModel.drinks.observe(viewLifecycleOwner) { drinks ->
            if (!drinks.isNullOrEmpty()) {
                binding.drinkRecipesRecyclerView.setItemViewCacheSize(drinks.size)
                viewModel.setAdapter(requireContext())
            }
        }
    }
}