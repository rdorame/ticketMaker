package com.example.ticketmaker.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.Class.ProductItem
import com.example.ticketmaker.R
import kotlinx.android.synthetic.main.products_in_ticket.view.*
import java.lang.StrictMath.round
import java.lang.String.format

class TicketProductAdapter(context: Context, private val myDataset: ArrayList<ProductItem>) : RecyclerView.Adapter<TicketProductAdapter.ViewHolder>() {
    private val cont = context
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewNS: TextView = itemView._ns
        var textViewDscr: TextView = itemView.dscr
        var textViewClave: TextView = itemView._clave
        var textViewP_Unit: TextView = itemView._punit
        var textViewCant: TextView = itemView._cant
        var textViewSubTot : TextView = itemView._subtotal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(cont).inflate(R.layout.products_in_ticket, parent, false))
    }

    fun removeItem(position: Int) {
        myDataset.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(product: ProductItem, position: Int) {
        myDataset.add(position, product)
        notifyItemInserted(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct: ProductItem = myDataset[position]

        holder.textViewNS.text = currentProduct.ns
        holder.textViewDscr.text = currentProduct.dscr.toUpperCase()
        holder.textViewCant.text = currentProduct.cant.toString()
        holder.textViewP_Unit.text = currentProduct.p_unit.toString()
        holder.textViewSubTot.text = "+" + format("%.2f", currentProduct.subtotal)
        holder.textViewClave.text = currentProduct.clave.toUpperCase()
    }

    override fun getItemCount() = myDataset.size
}