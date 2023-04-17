package com.boss.yummyfood.viewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boss.yummyfood.db.MealDatabase
import com.boss.yummyfood.pojo.*
import com.boss.yummyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(val mealDatabase: MealDatabase) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularMealLiveData = MutableLiveData<List<CategoryMeal>>()
    private var mealByCategory = MutableLiveData<List<MealsByCategory>>()
    private var favoritesMealLiveData = mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private val searchLiveData = MutableLiveData<List<Meal>>()


    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    private var saveStateOfRandomMeal: Meal? = null

    fun getRandomMeal() {
        saveStateOfRandomMeal?.let {
            randomMealLiveData.postValue(it)
            return
        }
        RetrofitInstance.mealApi.randomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                    saveStateOfRandomMeal = randomMeal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                return
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun getPopularMeal() {
        RetrofitInstance.mealApi.getPopularItems("Indian")
            .enqueue(object : Callback<CategoryMealList> {
                override fun onResponse(
                    call: Call<CategoryMealList>,
                    response: Response<CategoryMealList>,
                ) {
                    if (response.body() != null) {
                        popularMealLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                    return
                }
            })
    }

    fun observePopularMealLiveData(): LiveData<List<CategoryMeal>> {
        return popularMealLiveData
    }

    fun getMealByCategory() {
        RetrofitInstance.mealApi.getMealByCategory()
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>,
                ) {
                    response.body()?.let { category ->
                        mealByCategory.postValue(category.categories)
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun observeCategoryLiveData(): LiveData<List<MealsByCategory>> {
        return mealByCategory
    }

    fun observeFavoritesMealLiveData(): LiveData<List<Meal>> {
        return favoritesMealLiveData
    }

    fun getMealById(id: String) {
        RetrofitInstance.mealApi.getMealById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()

                meal?.let { meal ->
                    bottomSheetMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                return
            }
        })
    }

    fun observeBottomSheetMealLiveData(): LiveData<Meal> {
        return bottomSheetMealLiveData
    }

    fun searchMeal(searchName: String) {
        RetrofitInstance.mealApi.searchMeals(searchName).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals
                meal?.let {
                    searchLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                return
            }
        })
    }

    fun observeSearchLiveData(): LiveData<List<Meal>> {
        return searchLiveData
    }

}