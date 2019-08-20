package com.example.ticketmaker.Room.Dataclass

import androidx.room.*
import com.example.ticketmaker.Class.ProductItem
import java.util.*

@Entity(tableName = "ticket_table")
data class Ticket(
    @ColumnInfo(name="sucursal")
    var sucursal: String?,

    @ColumnInfo(name="ruta")
    var ruta: String?,

    @ColumnInfo(name="date")
    var date: Date?,

    @ColumnInfo(name="vendedor")
    var vendedor: String?,

    @ColumnInfo(name="folio_factura")
    var folio_factura: Int?,

    @ColumnInfo(name="factura")
    var factura: Int?,

    @ColumnInfo(name="client_code")
    var client_code: String?,

    @ColumnInfo(name="client_name")
    var client_name: String?,

    @ColumnInfo(name="documento")
    var documento: String?,

    @ColumnInfo(name="products")
    var products: ArrayList<ProductItem>?,

    @ColumnInfo(name="total")
    var total: Double
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}