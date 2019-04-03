package com.example.ticketmaker.Room


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product)

    @Query("SELECT * from product_table where code = :productCode ORDER BY code ASC")
    fun getProductsByCode(productCode : String) : LiveData<List<Product>>

    @Update
    fun update(product: Product)

    @Query("DELETE FROM product_table")
    fun deleteAllProducts()

    @Delete
    fun delete(product: Product)
}