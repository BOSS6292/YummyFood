package com.boss.yummyfood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.boss.yummyfood.pojo.Meal
import com.boss.yummyfood.pojo.MealList
import com.boss.yummyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsViewModel : ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id: String) {
        RetrofitInstance.mealApi.getMealById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

}