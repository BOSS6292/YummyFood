package com.boss.yummyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boss.yummyfood.databinding.PopularItemsBinding
import com.boss.yummyfood.pojo.CategoryMeal
import com.bumptech.glide.Glide

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.MostPopularMealViewHolder>() {

    lateinit var onItemClick:((CategoryMeal)->Unit)
    private var mealList = ArrayList<CategoryMeal>()

    fun setMeals(mealList: ArrayList<CategoryMeal>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularMealViewHolder {
        return MostPopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MostPopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopular)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class MostPopularMealViewHolder(var binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root)
}