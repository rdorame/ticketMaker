package com.example.ticketmaker.Room.Repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.ticketmaker.Room.AppDatabase
import com.example.ticketmaker.Room.Dao.ProductDao
import com.example.ticketmaker.Room.Dataclass.Product

class ProductRepository(application: Application) {
    private var productDao : ProductDao
    private var allProducts : LiveData<List<Product>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        productDao = database.productDao()
        allProducts =  productDao.getAllProducts()
    }

    fun insert(product: Product) {
        val insertProductAsyncTask = InsertProductAsyncTask(
            productDao
        ).execute(product)
    }

    fun update(product: Product) {
        val updateProductAsyncTask = UpdateProductAsyncTask(
            productDao
        ).execute(product)
    }


    fun delete(product: Product) {
        val deleteProductAsyncTask = DeleteProductAsyncTask(
            productDao
        ).execute(product)
    }

    fun deleteAllProducts() {
        val deleteAllProductsAsyncTask = DeleteAllProductsAsyncTask(
            productDao
        ).execute()
    }

    fun getAllProducts(): LiveData<List<Product>> {
        return allProducts
    }

    companion object {
        private class InsertProductAsyncTask(productDao: ProductDao) : AsyncTask<Product, Unit, Unit>() {
            val productDao = productDao

            override fun doInBackground(vararg p0: Product?) {
                productDao.insert(p0[0]!!)
            }
        }

        private class UpdateProductAsyncTask(productDao: ProductDao) : AsyncTask<Product, Unit, Unit>() {
            val productDao = productDao

            override fun doInBackground(vararg p0: Product?) {
                productDao.update(p0[0]!!)
            }
        }

        private class DeleteProductAsyncTask(productDao: ProductDao) : AsyncTask<Product, Unit, Unit>() {
            val productDao = productDao

            override fun doInBackground(vararg p0: Product?) {
                productDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllProductsAsyncTask(productDao: ProductDao) : AsyncTask<Unit, Unit, Unit>() {
            val productDao = productDao

            override fun doInBackground(vararg p0: Unit?) {
                productDao.deleteAllProducts()
            }
        }
    }



}