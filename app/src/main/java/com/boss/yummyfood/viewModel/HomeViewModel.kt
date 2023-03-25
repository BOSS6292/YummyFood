package com.boss.yummyfood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boss.yummyfood.pojo.*
import com.boss.yummyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularMealLiveData = MutableLiveData<List<CategoryMeal>>()
    private var mealByCategory = MutableLiveData<MealsByCategoryList>()

    fun getRandomMeal() {
        RetrofitInstance.mealApi.randomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun getPopularMeal(){
        RetrofitInstance.mealApi.getPopularItems("Indian").enqueue(object : Callback<CategoryMealList>{
            override fun onResponse(
                call: Call<CategoryMealList>,
                response: Response<CategoryMealList>,
            ) {
                if(response.body() != null){
                    popularMealLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun observePopularMealLiveData() : LiveData<List<CategoryMeal>>{
        return popularMealLiveData
    }

    fun getMealByCategory(){
        RetrofitInstance.mealApi.getMealByCategory().enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>,
            ) {
                response.body().let {
                    mealByCategory.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}