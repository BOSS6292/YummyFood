package com.boss.yummyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.boss.yummyfood.activities.MainActivity
import com.boss.yummyfood.adapters.FavoritesAdapter
import com.boss.yummyfood.databinding.FragmentFavoritesBinding
import com.boss.yummyfood.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class favoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoritesAdapter.differ.currentList[position]
                viewModel.deleteMeal(deletedMeal)

                Snackbar.make(requireView(), "Meal is Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo"
                    ) {
                        viewModel.insertMeal(deletedMeal)
                    }.show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoritesAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoritesMealLiveData().observe(requireActivity()) { meals ->
            favoritesAdapter.differ.submitList(meals)
        }
    }
}