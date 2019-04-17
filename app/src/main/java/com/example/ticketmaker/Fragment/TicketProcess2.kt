package com.example.ticketmaker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.Adapter.ProductToAddAdapter
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.R
import com.example.ticketmaker.Room.Dataclass.Product
import com.example.ticketmaker.Room.ViewModel.ProductViewModel

class TicketProcess2: Fragment() {

    private var prefs : SharedPrefs? = null
    private var rootView: View? = null
    private lateinit var productViewModel: ProductViewModel
    private var recycler_view : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_ticket_process_2, container, false)
        prefs = SharedPrefs(activity!!.baseContext)

        recycler_view = rootView!!.findViewById<View>(R.id.recycler_view) as RecyclerView

        recycler_view!!.layoutManager = LinearLayoutManager(activity!!.baseContext)
        recycler_view!!.setHasFixedSize(true)

        val adapter = ProductToAddAdapter()
        recycler_view!!.adapter = adapter

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.getAllProducts().observe(this, Observer<List<Product>> {
            adapter.submitList(it)
        })

        /*ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //productViewModel.delete(adapter.getProductAt(viewHolder.adapterPosition))
                Toast.makeText(activity!!.baseContext, "Product Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)
        */
        adapter.setOnItemClickListener(object : ProductToAddAdapter.OnItemClickListener {
            override fun onItemClick(product: Product) {
               /* var intent = Intent(activity!!.baseContext, AddEditProductActivity::class.java)
                intent.putExtra(AddEditProductActivity.EXTRA_ID, product.id)
                intent.putExtra(AddEditProductActivity.EXTRA_CODE, product.code)
                intent.putExtra(AddEditProductActivity.EXTRA_PRICE, product.price)
                intent.putExtra(AddEditProductActivity.EXTRA_DESCRIPTION, product.dscr)

                startActivityForResult(intent,
                    ViewProducts.EDIT_PRODUCT_REQUEST
                )*/
            }
        })

        return rootView
    }
}