package com.example.ticketmaker.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @ColumnInfo(name="code")
    var code: String?,

    @ColumnInfo(name="price")
    var price: Double,

    @ColumnInfo(name="dscr")
    var dscr: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}