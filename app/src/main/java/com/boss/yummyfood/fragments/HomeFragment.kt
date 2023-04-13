package com.boss.yummyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.boss.yummyfood.activities.RandomMealActivity
import com.boss.yummyfood.adapters.CategoriesAdapter
import com.boss.yummyfood.adapters.MostPopularAdapter
import com.boss.yummyfood.databinding.FragmentHomeBinding
import com.boss.yummyfood.pojo.CategoryMeal
import com.boss.yummyfood.pojo.Meal
import com.boss.yummyfood.viewModel.HomeViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homemvmm: HomeViewModel
    private lateinit var randomeMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "1"
        const val MEAL_NAME = "NAME"
        const val MEAL_IMAGE = "IMAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homemvmm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        poplarItemRecyclerView()
        homemvmm.getRandomMeal()
        observeRandomLiveData()
        onRandomMealClick()

        homemvmm.getPopularMeal()
        observePopularLiveData()
        onPopularItemClick()

        prepareCategoryRecyclerView()
        homemvmm.getMealByCategory()
        observeCategoryLiveData()


    }

    private fun prepareCategoryRecyclerView() {
        categoryAdapter = CategoriesAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observeCategoryLiveData() {
        homemvmm.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = { meal ->
            val intent = Intent(activity, RandomMealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_IMAGE, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun poplarItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observePopularLiveData() {
        homemvmm.observePopularMealLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemAdapter.setMeals(mealList = mealList as ArrayList<CategoryMeal>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, RandomMealActivity::class.java)
            intent.putExtra(MEAL_ID, randomeMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomeMeal.strMeal)
            intent.putExtra(MEAL_IMAGE, randomeMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomLiveData() {
        homemvmm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomeMeal = meal

        }
    }
}
