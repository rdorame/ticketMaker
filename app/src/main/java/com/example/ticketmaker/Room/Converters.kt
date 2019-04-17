package com.example.ticketmaker.Room

import androidx.room.TypeConverter
import com.example.ticketmaker.Class.ProductItem
import com.example.ticketmaker.Room.Dataclass.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.*


class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromString(value : String) : ArrayList<ProductItem>{
            val listType = object : TypeToken<ArrayList<ProductItem>>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromArrayLisr(list: ArrayList<ProductItem>): String {
            val gson = Gson()
            return gson.toJson(list)
        }


        @TypeConverter
        @JvmStatic
        fun fromDate(date: Date): String? {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            return dateFormat.format(date)
        }

        @TypeConverter
        @JvmStatic
        fun toDate(value: String): Date {
            return SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(value)
        }
    }
}