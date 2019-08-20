package com.example.ticketmaker.Products

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.Adapter.ProductAdapter
import com.example.ticketmaker.R
import com.example.ticketmaker.Room.Dataclass.Product
import com.example.ticketmaker.Room.ViewModel.ProductViewModel

import kotlinx.android.synthetic.main.activity_view_products.*

class ViewProducts : AppCompatActivity() {

    companion object {
        const val ADD_PRODUCT_REQUEST = 1
        const val EDIT_PRODUCT_REQUEST = 2
    }

    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_products)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivityForResult(
                Intent(this, AddEditProductActivity::class.java),
                ADD_PRODUCT_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter = ProductAdapter()
        recycler_view.adapter = adapter

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.getAllProducts().observe(this, Observer<List<Product>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                productViewModel.delete(adapter.getProductAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Product Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(product: Product) {
                var intent = Intent(baseContext, AddEditProductActivity::class.java)
                intent.putExtra(AddEditProductActivity.EXTRA_ID, product.id)
                intent.putExtra(AddEditProductActivity.EXTRA_CODE, product.code)
                intent.putExtra(AddEditProductActivity.EXTRA_PRICE, product.price)
                intent.putExtra(AddEditProductActivity.EXTRA_DESCRIPTION, product.dscr)

                startActivityForResult(intent,
                    EDIT_PRODUCT_REQUEST
                )
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_PRODUCT_REQUEST && resultCode == Activity.RESULT_OK) {
            val newProduct = Product(
                data!!.getStringExtra(AddEditProductActivity.EXTRA_CODE),
                data.getDoubleExtra(AddEditProductActivity.EXTRA_PRICE, 1.0),
                data.getStringExtra(AddEditProductActivity.EXTRA_DESCRIPTION)
            )
            productViewModel.insert(newProduct)

            Toast.makeText(this, "Product saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_PRODUCT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditProductActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateProduct = Product(
                data!!.getStringExtra(AddEditProductActivity.EXTRA_CODE),
                data.getDoubleExtra(AddEditProductActivity.EXTRA_PRICE, 1.0),
                data.getStringExtra(AddEditProductActivity.EXTRA_DESCRIPTION)
            )
            updateProduct.id = data.getIntExtra(AddEditProductActivity.EXTRA_ID, -1)
            productViewModel.update(updateProduct)

        } else {
            Toast.makeText(this, "Product not saved!", Toast.LENGTH_SHORT).show()
        }

    }

}
