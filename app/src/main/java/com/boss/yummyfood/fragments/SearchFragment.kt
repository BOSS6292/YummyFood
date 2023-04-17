package com.boss.yummyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.boss.yummyfood.R
import com.boss.yummyfood.activities.MainActivity
import com.boss.yummyfood.adapters.CategoriesAdapter
import com.boss.yummyfood.adapters.FavoritesAdapter
import com.boss.yummyfood.databinding.FragmentSearchBinding
import com.boss.yummyfood.viewModel.HomeViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchViewAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        binding.searchArrow.setOnClickListener {
            searchMeals()
        }
        observeSearchLiveData()
    }

    private fun observeSearchLiveData() {
        viewModel.observeSearchLiveData().observe(viewLifecycleOwner, Observer {
            searchViewAdapter.differ.submitList(it)
        })
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchViewAdapter = FavoritesAdapter()
        binding.recSearch.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchViewAdapter
        }
    }
}