package com.example.ticketmaker.Ticket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.Adapter.TicketAdapter
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.LoginActivity
import com.example.ticketmaker.Products.ViewProducts
import com.example.ticketmaker.R
import com.example.ticketmaker.Room.Dataclass.Ticket
import com.example.ticketmaker.Room.ViewModel.TicketViewModel

import kotlinx.android.synthetic.main.activity_ticket_list.*
import kotlinx.android.synthetic.main.content_ticket_list.*

class TicketList : AppCompatActivity() {
    //val ticketList : RecyclerView = RecyclerView()
    var prefs : SharedPrefs? = null

    companion object {
        const val ADD_TICKET_REQUEST = 1
        const val EDIT_TICKET_REQUEST = 2
    }

    private lateinit var ticketViewModel: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_list)
        setSupportActionBar(toolbar)

        prefs = SharedPrefs(baseContext)

        fab.setOnClickListener { view ->
            val intent = Intent(this, TicketSelection::class.java)
            startActivityForResult(intent, 100)
        }

        ticket_list.layoutManager = LinearLayoutManager(this)
        ticket_list.setHasFixedSize(true)

        val adapter = TicketAdapter()
        ticket_list.adapter = adapter

        ticketViewModel = ViewModelProviders.of(this).get(TicketViewModel::class.java)
        ticketViewModel.getAllTickets().observe(this, Observer<List<Ticket>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                ticketViewModel.delete(adapter.getTicketAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Ticket Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(ticket_list)

        /*adapter.setOnItemClickListener(object : TicketAdapter.OnItemClickListener {
            override fun onItemClick(ticket: Ticket) {
                var intent = Intent(baseContext, AddEditTicketActivity::class.java)
                intent.putExtra(AddEditTicketActivity.EXTRA_ID, ticket.id)
                intent.putExtra(AddEditTicketActivity.EXTRA_CODE, ticket.code)
                intent.putExtra(AddEditTicketActivity.EXTRA_PRICE, ticket.price)
                intent.putExtra(AddEditTicketActivity.EXTRA_DESCRIPTION, ticket.dscr)

                startActivityForResult(intent,
                    ViewTickets.EDIT_TICKET_REQUEST
                )
            }
        })*/
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
                //prefs!!.resetCredentials(baseContext)
                prefs!!.logOut(baseContext)
                finish()
                startActivity(Intent(this@TicketList, LoginActivity::class.java))
            }
        }
        return true
    }
}
