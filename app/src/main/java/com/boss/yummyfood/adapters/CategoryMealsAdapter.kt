package com.boss.yummyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boss.yummyfood.databinding.MealItemBinding
import com.boss.yummyfood.pojo.CategoryMeal
import com.bumptech.glide.Glide

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoriesViewHolder>() {

    private var mealList = ArrayList<CategoryMeal>()

    fun setMeals(mealList: List<CategoryMeal>) {
        this.mealList = mealList as ArrayList<CategoryMeal>
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}