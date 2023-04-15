package com.boss.yummyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boss.yummyfood.db.MealDatabase

class MealViewModelFactory(val mealDatabase: MealDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailsViewModel(mealDatabase) as T
    }
}