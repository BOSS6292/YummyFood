package com.boss.yummyfood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun anyToString(attribute: Any?): String {
        if (attribute == null) {
            return ""
        }
        return attribute as String
    }

    @TypeConverter
    fun stringToAny(attribute: String?): Any {
        if (attribute == null) {
            return ""
        }
        return attribute
    }
}