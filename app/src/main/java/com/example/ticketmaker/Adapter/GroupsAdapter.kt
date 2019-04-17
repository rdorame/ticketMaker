package com.example.ticketmaker.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.R
import kotlinx.android.synthetic.main.groups_item.view.*
import java.lang.String

class GroupsAdapter (context: Context, private val groups: ArrayList<Double>, private val cants: ArrayList<Int>) : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
    private val cont = context
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewPUNIT: TextView = itemView.punitT
        var textViewCANT: TextView = itemView.cantT
        var textViewSUBTOTAL: TextView = itemView.subtotalT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(cont).inflate(R.layout.groups_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGroup: Double = groups[position]
        val currentCant: Int = cants[position]

        holder.textViewPUNIT.text = currentGroup.toString()
        holder.textViewCANT.text = currentCant.toString()
        holder.textViewSUBTOTAL.text = "+" + String.format("%.2f", (currentCant * currentGroup))
    }

    override fun getItemCount() = groups.size
}