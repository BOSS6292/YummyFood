package com.boss.yummyfood.retrofit

import com.boss.yummyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun randomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id: String): Call<MealList>

}