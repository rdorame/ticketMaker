package com.example.ticketmaker.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.R
import com.example.ticketmaker.Room.Dataclass.Ticket
import kotlinx.android.synthetic.main.product_item.view.*
import java.text.SimpleDateFormat

class TicketAdapter : ListAdapter<Ticket, TicketAdapter.TicketHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ticket>() {
            override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
                return oldItem.folio_factura == newItem.folio_factura && oldItem.total == newItem.total
                        && oldItem.date == newItem.date
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return TicketHolder(itemView)
    }

    override fun onBindViewHolder(holder: TicketHolder, position: Int) {
        val currentTicket: Ticket = getItem(position)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        holder.textViewFolio.text = currentTicket.folio_factura.toString()
        holder.textViewTotal.text = currentTicket.total.toString()
        holder.textViewDate.text = dateFormat.format(currentTicket.date)
    }

    fun getTicketAt(position: Int): Ticket {
        return getItem(position)
    }

    inner class TicketHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewFolio: TextView = itemView.text_view_code
        var textViewTotal: TextView = itemView.text_view_price
        var textViewDate: TextView = itemView.text_view_description
    }

    interface OnItemClickListener {
        fun onItemClick(product: Ticket)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}