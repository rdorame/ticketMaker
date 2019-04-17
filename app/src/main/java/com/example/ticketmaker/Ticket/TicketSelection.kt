package com.example.ticketmaker.Ticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ticketmaker.R

class TicketSelection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_selection)

        val intent = Intent(baseContext, GeneralInfo::class.java)
        intent.putExtra("type", 2)
        startActivityForResult(intent, 100)
    }

    fun factura(v : View){
        val intent = Intent(baseContext, GeneralInfo::class.java)
        intent.putExtra("type", 1)
        startActivityForResult(intent, 100)
    }

    fun remision(v : View){
        val intent = Intent(baseContext, GeneralInfo::class.java)
        intent.putExtra("type", 2)
        startActivityForResult(intent, 100)
    }
}
