package com.boss.yummyfood.retrofit

import com.boss.yummyfood.pojo.CategoryMealList
import com.boss.yummyfood.pojo.MealList
import com.boss.yummyfood.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun randomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("a") categoryName: String): Call<CategoryMealList>

    @GET("categories.php")
    fun getMealByCategory(): Call<MealsByCategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<CategoryMealList>

}