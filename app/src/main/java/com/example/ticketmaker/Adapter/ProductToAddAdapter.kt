package com.example.ticketmaker.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.Class.ProductItem
import com.example.ticketmaker.R
import com.example.ticketmaker.Room.Dataclass.Product
import kotlinx.android.synthetic.main.product_to_add.view.*

class ProductToAddAdapter : ListAdapter<Product, ProductToAddAdapter.ProductHolder>(DIFF_CALLBACK) {
    var arrayListProductsToAdd = ArrayList<ProductItem>()

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.code == newItem.code && oldItem.dscr == newItem.dscr
                        && oldItem.price == newItem.price
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.product_to_add, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentProduct: Product = getItem(position)

        holder.textViewCode.text = currentProduct.code!!.toUpperCase()
        holder.textViewPrice.text = "$" + currentProduct.price.toString()
        holder.textViewDescription.text = currentProduct.dscr!!.toUpperCase()
        holder.qtyView.text = 0.toString()
        holder.lessBtn.setOnClickListener {
          modifyQty(0, holder, position)
        }
        holder.addBtn.setOnClickListener {
            modifyQty(1, holder, position)
        }

        var strPos = ""
        if (position+1< 10){
            strPos = "0${position+1}"
        }
        else{
            strPos = "${position+1}"
        }

        arrayListProductsToAdd.add(ProductItem(currentProduct.dscr!!, strPos, currentProduct.code!!, 0, currentProduct.price, 0.0))
    }

    private fun modifyQty(type : Int, holder: ProductHolder, position : Int){
        val currentAmount = holder.qtyView.text.toString().toDouble()
        when(type){
            0 -> {
                if (currentAmount > 0){
                    holder.qtyView.text = (currentAmount - 1).toString().split(".")[0]
                }
            }
            1 -> {
                    holder.qtyView.text = (currentAmount + 1).toString().split(".")[0]
            }
        }

        arrayListProductsToAdd[position].cant = holder.qtyView.text.toString().toInt()
    }

    fun getProductToAddAt(position: Int): ProductItem {
        return arrayListProductsToAdd[position]
    }

    fun getProductAt(position: Int): Product {
        return getItem(position)
    }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewCode: TextView = itemView.text_view_code
        var textViewPrice: TextView = itemView.text_view_price
        var textViewDescription: TextView = itemView.text_view_description
        var qtyView: TextView = itemView.qtyView
        var addBtn: TextView = itemView.btnPlus
        var lessBtn: TextView = itemView.btnLess
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}