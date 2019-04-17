package com.example.ticketmaker.Ticket

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.MainActivity
import com.example.ticketmaker.Products.AddEditProductActivity
import com.example.ticketmaker.Products.ViewProducts
import com.example.ticketmaker.R
import com.example.ticketmaker.Room.Dataclass.Product
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_ticket_process.*

class GeneralInfo : AppCompatActivity() {
    private var prefs : SharedPrefs? = null

    var factura : EditText? = null
    var folioFactura : EditText? = null
    var remision : EditText? = null
    var sucursal : EditText? = null
    var idRuta : EditText? = null
    var documento : EditText? = null
    var idCliente : EditText? = null
    var nombreCliente : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_info)

        prefs = SharedPrefs(baseContext)


        factura = findViewById<View>(R.id.factura) as EditText
        folioFactura = findViewById<View>(R.id.folioFactura) as EditText
        remision = findViewById<View>(R.id.remision) as EditText
        sucursal = findViewById<View>(R.id.sucursal) as EditText
        idRuta = findViewById<View>(R.id.idRuta) as EditText
        documento = findViewById<View>(R.id.documento) as EditText
        idCliente = findViewById<View>(R.id.idCliente) as EditText
        nombreCliente = findViewById<View>(R.id.nombreCliente) as EditText

        if(intent.hasExtra("type")){
            if (intent.getIntExtra("type", -1) == 1){
                remision!!.visibility = View.GONE
            }
            else if (intent.getIntExtra("type", -1) == 2){
                factura!!.visibility = View.GONE
                folioFactura!!.visibility = View.GONE
            }
        }

        fab.setOnClickListener { view ->
            if(validate()){
                if (intent.getIntExtra("type", -1) == 1){
                    prefs!!.save(baseContext, "factura", factura!!.text.toString())
                    prefs!!.save(baseContext, "folioFactura", folioFactura!!.text.toString())
                }
                else if (intent.getIntExtra("type", -1) == 2){
                    prefs!!.save(baseContext, "remision", remision!!.text.toString())
                }
                prefs!!.save(baseContext, "ticket_type", intent.getIntExtra("type", -1))
                prefs!!.save(baseContext, "sucursal", sucursal!!.text.toString())
                prefs!!.save(baseContext, "idRuta", idRuta!!.text.toString())
                prefs!!.save(baseContext, "documento", documento!!.text.toString())
                prefs!!.save(baseContext, "idCliente", idCliente!!.text.toString())
                prefs!!.save(baseContext, "nombreCliente", nombreCliente!!.text.toString())
                prefs!!.save(baseContext, "fecha", nombreCliente!!.text.toString())
                
                startActivityForResult(Intent(this,  SelectProducts::class.java), 100)
            }
            else{
                Toast.makeText(baseContext, "Por favor completa la información requerida antes de proceder", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate(): Boolean {
        if( sucursal!!.text.toString() == ""
            || idRuta!!.text.toString() == ""
            || documento!!.text.toString() == ""
            || idCliente!!.text.toString() == ""
            || nombreCliente!!.text.toString() == ""
        ){
            return false
        } else if (intent.getIntExtra("type", -1) == 1) {
            if (factura!!.text.toString() == ""
                || folioFactura!!.text.toString() == ""){
                return false
            }
        } else if(intent.getIntExtra("type", -1) == 2){
            if(remision!!.text.toString() == ""){
                return false
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

        }
        else{
            Toast.makeText(baseContext, "Regresando a información general", Toast.LENGTH_SHORT).show()
        }

    }


}
