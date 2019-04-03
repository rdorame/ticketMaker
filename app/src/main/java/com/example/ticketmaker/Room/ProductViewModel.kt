package com.example.ticketmaker.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel (application: Application) : AndroidViewModel(application){
    private var repository: ProductRepository =
        ProductRepository(application)
    private var allProducts: LiveData<List<Product>> = repository.getAllProducts()

    fun insert(note: Product) {
        repository.insert(note)
    }

    fun update(note: Product) {
        repository.update(note)
    }

    fun delete(note: Product) {
        repository.delete(note)
    }

    fun deleteAllProducts() {
        repository.deleteAllProducts()
    }

    fun getAllProducts(): LiveData<List<Product>> {
        return allProducts
    }
}