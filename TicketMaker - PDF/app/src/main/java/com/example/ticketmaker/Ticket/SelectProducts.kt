package com.example.ticketmaker.Ticket

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.Adapter.ProductToAddAdapter
import com.example.ticketmaker.Class.ProductItem
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.R
import com.example.ticketmaker.Room.Dataclass.Product
import com.example.ticketmaker.Room.ViewModel.ProductViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_ticket_process.*
import java.util.*
import kotlin.collections.ArrayList
import com.example.ticketmaker.MainActivity
import com.example.ticketmaker.SetPrinter


class SelectProducts : AppCompatActivity() {

    private var prefs : SharedPrefs? = null
    private lateinit var productViewModel: ProductViewModel
    private var recycler_view : RecyclerView? = null
    private var arrayListProductsSelected : ArrayList<ProductItem>? = ArrayList<ProductItem>()
    var groups : ArrayList<Double>? = ArrayList<Double>()
    var cants : ArrayList<Int>? = ArrayList<Int>()
    private var adapter : ProductToAddAdapter? = null
    var orderArray : ArrayList<Int>? = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_products)
        Log.i("Position Count", "Start activity")

        prefs = SharedPrefs(baseContext)
        adapter = ProductToAddAdapter()

        recycler_view = findViewById<View>(R.id.recycler_view) as RecyclerView

        recycler_view!!.layoutManager = LinearLayoutManager(baseContext)
        recycler_view!!.setHasFixedSize(true)

        recycler_view!!.adapter = adapter

        recycler_view!!.setItemViewCacheSize(100)
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.getAllProducts().observe(this, Observer<List<Product>> {
            adapter!!.submitList(it)
        })

        fab.setOnClickListener {
            addToArrayList()
            startActivityForResult(Intent(this,  TicketTemplate::class.java), 100)
            //startActivityForResult(Intent(this,  MainActivity::class.java), 100)
        }
    }

    private fun addToArrayList(){
        val gson = Gson()
        orderArray = adapter!!.getOrder()
        Log.d("Order", gson.toJson(orderArray))


        val finalOrderArray = ArrayList<Int>()
        for (j in 0 until orderArray!!.size){
            if (adapter!!.getProductToAddAt(orderArray!![j]).cant > 0){
                finalOrderArray.add(orderArray!![j])
            }
        }

        var nsPlux = 0
        for (k in 0 until finalOrderArray.size){
            nsPlux += 1

            val nsToAdd = if (nsPlux > 9){
                nsPlux.toString()
            }
            else{
                "0$nsPlux"
            }
            val productItem = adapter!!.getProductToAddAt(finalOrderArray[k])
            arrayListProductsSelected!!.add(ProductItem(productItem.dscr, nsToAdd, productItem.clave, productItem.cant, productItem.p_unit, productItem.cant * productItem.p_unit))
        }

        val arrayListAsc : List<Int> = finalOrderArray.sortedWith(compareBy {it})
        Log.d("Order", gson.toJson(arrayListAsc))
        for (i in 0 until arrayListAsc.size){
            if(groups!!.contains(adapter!!.getProductToAddAt(arrayListAsc[i]).p_unit)){
                for (j in 0 until groups!!.size){
                    if(groups!![j] == adapter!!.getProductToAddAt(arrayListAsc[i]).p_unit  && adapter!!.getProductToAddAt(arrayListAsc[i]).cant > 0){
                        cants!![j] += adapter!!.getProductToAddAt(arrayListAsc[i]).cant
                    }
                }
            }
            if(!groups!!.contains(adapter!!.getProductToAddAt(arrayListAsc[i]).p_unit) && adapter!!.getProductToAddAt(arrayListAsc[i]).cant > 0){
                groups!!.add(adapter!!.getProductToAddAt(arrayListAsc[i]).p_unit)
                cants!!.add(adapter!!.getProductToAddAt(arrayListAsc[i]).cant)
            }
        }


       // val itemAmount = arrayListAsc.size
        //var ns = 0
        /*for (i in 0 until itemAmount){
            if (adapter!!.getProductToAddAt(i).cant > 0){
                ns += 1

                val nsToAdd = if (ns > 9){
                    ns.toString()
                }
                else{
                    "0$ns"
                }

                val productItem = adapter!!.getProductToAddAt(i)
                arrayListProductsSelected!!.add(ProductItem(productItem.dscr, nsToAdd, productItem.clave, productItem.cant, productItem.p_unit, productItem.cant * productItem.p_unit))
            }
            if(groups!!.contains(adapter!!.getProductToAddAt(i).p_unit) && adapter!!.getProductToAddAt(i).cant > 0){
                for (j in 0 until groups!!.size){
                    if(groups!![j] == adapter!!.getProductToAddAt(i).p_unit){
                        cants!![j] += adapter!!.getProductToAddAt(i).cant
                    }
                }
            }
            if(!groups!!.contains(adapter!!.getProductToAddAt(i).p_unit) && adapter!!.getProductToAddAt(i).cant > 0){
                groups!!.add(adapter!!.getProductToAddAt(i).p_unit)
                cants!!.add(adapter!!.getProductToAddAt(i).cant)
            }
        }*/
        for(i in 0 until groups!!.size){
            Log.e("OUTPUT", "${groups!![i]}, ${cants!![i]}")
        }

        val str = gson.toJson(arrayListProductsSelected)
        val strG = gson.toJson(groups)
        val strC = gson.toJson(cants)
        prefs!!.save(baseContext, "productsAdded", str)
        prefs!!.save(baseContext, "groupsAdded", strG)
        prefs!!.save(baseContext, "cantsAdded", strC)
        Log.e("Products added", str)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data)
            finish()
        }
        else{
            Toast.makeText(baseContext, "Regresando a seleccionar productos", Toast.LENGTH_SHORT).show()
        }

    }
}
