package com.boss.yummyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boss.yummyfood.databinding.CategoryItemBinding
import com.boss.yummyfood.databinding.PopularItemsBinding
import com.boss.yummyfood.pojo.CategoryMeal
import com.boss.yummyfood.pojo.CategoryMealList
import com.boss.yummyfood.pojo.MealsByCategory
import com.boss.yummyfood.pojo.MealsByCategoryList
import com.bumptech.glide.Glide

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoryList = ArrayList<MealsByCategory>()

    fun setCategoryList(categoryList: List<MealsByCategory>) {
        this.categoryList = categoryList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}