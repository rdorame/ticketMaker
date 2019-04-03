package com.example.ticketmaker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.Products.ViewProducts

import kotlinx.android.synthetic.main.activity_ticket_list.*

class TicketList : AppCompatActivity() {
    //val ticketList : RecyclerView = RecyclerView()
    var prefs : SharedPrefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_list)
        setSupportActionBar(toolbar)

        prefs = SharedPrefs(baseContext)

        fab.setOnClickListener { view ->
            val intent = Intent(this, TicketProcess::class.java)
            startActivityForResult(intent, 100)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_products -> {
                startActivity(Intent(this@TicketList, ViewProducts::class.java))
            }
            R.id.action_logout -> {
                prefs!!.resetCredentials(baseContext)
                finish()
                startActivity(Intent(this@TicketList, LoginActivity::class.java))
            }
        }
        return true
    }
}
