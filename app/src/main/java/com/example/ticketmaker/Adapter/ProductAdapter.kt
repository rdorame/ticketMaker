package com.example.ticketmaker.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.Room.Product
import com.example.ticketmaker.R
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductHolder>(DIFF_CALLBACK) {

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
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentProduct: Product = getItem(position)

        holder.textViewCode.text = currentProduct.code
        holder.textViewPrice.text = currentProduct.price.toString()
        holder.textViewDescription.text = currentProduct.dscr
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
    }

    interface OnItemClickListener {
        fun onItemClick(note: Product)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}