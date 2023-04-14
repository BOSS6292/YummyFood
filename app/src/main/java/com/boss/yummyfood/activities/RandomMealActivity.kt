package com.boss.yummyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.boss.yummyfood.R
import com.boss.yummyfood.databinding.ActivityRandomMealBinding
import com.boss.yummyfood.fragments.HomeFragment
import com.boss.yummyfood.pojo.Meal
import com.boss.yummyfood.pojo.MealList
import com.boss.yummyfood.viewModel.HomeViewModel
import com.boss.yummyfood.viewModel.MealDetailsViewModel
import com.bumptech.glide.Glide

class RandomMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRandomMealBinding
    private lateinit var meal_id: String
    private lateinit var meal_name: String
    private lateinit var meal_image: String
    private lateinit var youtubeLink : String
    private lateinit var mealmvmm: MealDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingCase()
        mealmvmm = ViewModelProvider(this)[MealDetailsViewModel::class.java]

        getInformationFromIntent()
        setInformationInViews()

        mealmvmm.getMealDetails(meal_id)

        observeMealDetailsLiveData()
        onYoutubeClick()
    }

    private fun onYoutubeClick(){
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        mealmvmm.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t

                binding.tvInstructionsDetails.text = meal!!.strInstructions
                binding.tvAreaDetails.text = "Area : ${meal!!.strArea}"
                binding.tvCategoryDetails.text = "Category ${meal!!.strCategory}"
                youtubeLink = meal.strYoutube.toString()
            }
        })
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

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvInstructionsDetails.visibility = View.INVISIBLE
        binding.tvCategoryDetails.visibility = View.INVISIBLE
        binding.tvAreaDetails.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvInstructionsDetails.visibility = View.VISIBLE
        binding.tvCategoryDetails.visibility = View.VISIBLE
        binding.tvAreaDetails.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}