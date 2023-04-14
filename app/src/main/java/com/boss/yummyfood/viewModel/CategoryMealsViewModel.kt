package com.boss.yummyfood.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.boss.yummyfood.pojo.CategoryMeal
import com.boss.yummyfood.pojo.CategoryMealList
import com.boss.yummyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<CategoryMeal>>()

    fun getMealByCategory(categoryName: String) {
        RetrofitInstance.mealApi.getMealsByCategory(categoryName)
            .enqueue(object : Callback<CategoryMealList> {
                override fun onResponse(
                    call: Call<CategoryMealList>,
                    response: Response<CategoryMealList>,
                ) {
                    response.body().let {
                        mealsLiveData.postValue(it!!.meals)
                    }
                }

                override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                    return
                }
            })
    }

    fun observeMealsLiveData(): LiveData<List<CategoryMeal>> {
        return mealsLiveData
    }

}