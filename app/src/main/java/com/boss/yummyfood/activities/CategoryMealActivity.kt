package com.boss.yummyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.boss.yummyfood.adapters.CategoryMealsAdapter
import com.boss.yummyfood.databinding.ActivityCategoryMealBinding
import com.boss.yummyfood.fragments.HomeFragment
import com.boss.yummyfood.viewModel.CategoryMealsViewModel

class CategoryMealActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealList ->
            categoryMealsAdapter.setMeals(mealList)
        })
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            adapter = categoryMealsAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}