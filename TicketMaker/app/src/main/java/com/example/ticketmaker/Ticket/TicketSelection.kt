package com.example.ticketmaker.Ticket

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data)
            finish()
        }
        else{
            Toast.makeText(baseContext, "Regresando a información general", Toast.LENGTH_SHORT).show()
        }

    }
}
