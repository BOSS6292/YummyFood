package com.boss.yummyfood.retrofit

import com.boss.yummyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun randomMeal(): Call<MealList>

}