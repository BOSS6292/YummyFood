package com.boss.yummyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.boss.yummyfood.activities.RandomMealActivity
import com.boss.yummyfood.databinding.FragmentHomeBinding
import com.boss.yummyfood.pojo.Meal
import com.boss.yummyfood.viewModel.HomeViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homemvmm: HomeViewModel
    private lateinit var randomeMeal: Meal

    companion object {
        const val MEAL_ID = "1"
        const val MEAL_NAME = "NAME"
        const val MEAL_IMAGE = "IMAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homemvmm = ViewModelProvider(this)[HomeViewModel::class.java]
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
        homemvmm.getRandomMeal()
        observeRandomLiveData()
        onRandomMealClick()
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

    fun observeRandomLiveData() {
        homemvmm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomeMeal = meal

        }
    }
}
