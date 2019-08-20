package com.example.ticketmaker.Room.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ticketmaker.Room.Dataclass.Product
import com.example.ticketmaker.Room.Repository.ProductRepository

class ProductViewModel (application: Application) : AndroidViewModel(application){
    private var repository: ProductRepository =
        ProductRepository(application)
    private var allProducts: LiveData<List<Product>> = repository.getAllProducts()

    fun insert(product: Product) {
        repository.insert(product)
    }

    fun update(product: Product) {
        repository.update(product)
    }

    fun delete(product: Product) {
        repository.delete(product)
    }

    fun deleteAllProducts() {
        repository.deleteAllProducts()
    }

    fun getAllProducts(): LiveData<List<Product>> {
        return allProducts
    }
}