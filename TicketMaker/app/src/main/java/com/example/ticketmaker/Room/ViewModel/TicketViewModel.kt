package com.example.ticketmaker.Room.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ticketmaker.Room.Dataclass.Ticket
import com.example.ticketmaker.Room.Repository.TicketRepository

class TicketViewModel (application: Application) : AndroidViewModel(application){
    private var repository: TicketRepository =
        TicketRepository(application)
    private var allTickets: LiveData<List<Ticket>> = repository.getAllTickets()

    fun insert(Ticket: Ticket) {
        repository.insert(Ticket)
    }

    fun update(Ticket: Ticket) {
        repository.update(Ticket)
    }

    fun delete(Ticket: Ticket) {
        repository.delete(Ticket)
    }

    fun deleteAllTickets() {
        repository.deleteAllTickets()
    }

    fun getAllTickets(): LiveData<List<Ticket>> {
        return allTickets
    }
}