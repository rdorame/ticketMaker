package com.example.ticketmaker.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ticketmaker.Room.Dataclass.Ticket

@Dao
interface TicketDao {

    @Query("SELECT * FROM ticket_table ORDER BY id DESC")
    fun getAllTickets(): LiveData<List<Ticket>>

    @Insert
    fun insertTicket(ticket: Ticket)

    @Query("SELECT * from ticket_table where folio_factura = :folio ORDER BY date ASC")
    fun getTicketsByID(folio : Int) : Ticket

    @Update
    fun updateTicket(ticket: Ticket)

    @Query("DELETE FROM ticket_table")
    fun deleteAllTickets()

    @Delete
    fun deleteTicket(ticket: Ticket)
}