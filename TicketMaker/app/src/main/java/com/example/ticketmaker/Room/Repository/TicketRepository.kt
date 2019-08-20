package com.example.ticketmaker.Room.Repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.ticketmaker.Room.AppDatabase
import com.example.ticketmaker.Room.Dao.TicketDao
import com.example.ticketmaker.Room.Dataclass.Ticket

class TicketRepository (application: Application) {
    private var ticketDao : TicketDao
    private var allTickets : LiveData<List<Ticket>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        ticketDao = database.ticketDao()
        allTickets =  ticketDao.getAllTickets()
    }

    fun insert(ticket: Ticket) {
        val insertTicketAsyncTask = InsertTicketAsyncTask(
            ticketDao
        ).execute(ticket)
    }

    fun update(ticket: Ticket) {
        val updateTicketAsyncTask = UpdateTicketAsyncTask(
            ticketDao
        ).execute(ticket)
    }


    fun delete(ticket: Ticket) {
        val deleteTicketAsyncTask = DeleteTicketAsyncTask(
            ticketDao
        ).execute(ticket)
    }

    fun deleteAllTickets() {
        val deleteAllTicketsAsyncTask = DeleteAllTicketsAsyncTask(
            ticketDao
        ).execute()
    }

    fun getAllTickets(): LiveData<List<Ticket>> {
        return allTickets
    }

    companion object {
        private class InsertTicketAsyncTask(ticketDao: TicketDao) : AsyncTask<Ticket, Unit, Unit>() {
            val ticketDao = ticketDao

            override fun doInBackground(vararg p0: Ticket?) {
                ticketDao.insertTicket(p0[0]!!)
            }
        }

        private class UpdateTicketAsyncTask(ticketDao: TicketDao) : AsyncTask<Ticket, Unit, Unit>() {
            val ticketDao = ticketDao

            override fun doInBackground(vararg p0: Ticket?) {
                ticketDao.updateTicket(p0[0]!!)
            }
        }

        private class DeleteTicketAsyncTask(ticketDao: TicketDao) : AsyncTask<Ticket, Unit, Unit>() {
            val ticketDao = ticketDao

            override fun doInBackground(vararg p0: Ticket?) {
                ticketDao.deleteTicket(p0[0]!!)
            }
        }

        private class DeleteAllTicketsAsyncTask(ticketDao: TicketDao) : AsyncTask<Unit, Unit, Unit>() {
            val ticketDao = ticketDao

            override fun doInBackground(vararg p0: Unit?) {
                ticketDao.deleteAllTickets()
            }
        }
    }



}