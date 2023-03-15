package com.boss.yummyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.boss.yummyfood.R
import com.boss.yummyfood.databinding.ActivityRandomMealBinding
import com.boss.yummyfood.fragments.HomeFragment
import com.boss.yummyfood.pojo.Meal
import com.bumptech.glide.Glide

class RandomMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRandomMealBinding
    private lateinit var meal_id: String
    private lateinit var meal_name:String
    private lateinit var meal_image:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getInformationFromIntent()
        setInformationInViews()
    }

    private fun setInformationInViews() {
        binding.collapsingToolbar.title = meal_name
        Glide.with(applicationContext).load(meal_image).into(binding.randomMealImage)
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getInformationFromIntent() {
        val intent = intent
        meal_id = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        meal_image = intent.getStringExtra(HomeFragment.MEAL_IMAGE)!!
        meal_name = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
    }
}