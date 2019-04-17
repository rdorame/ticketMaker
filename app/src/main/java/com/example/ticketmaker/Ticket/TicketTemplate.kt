package com.example.ticketmaker.Ticket

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketmaker.R
import kotlinx.android.synthetic.main.activity_ticket_template.*
import java.io.*
import kotlinx.android.synthetic.main.content_ticket_template.*
import android.graphics.*
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaker.Adapter.GroupsAdapter
import com.example.ticketmaker.Adapter.TicketProductAdapter
import com.example.ticketmaker.Class.ProductItem
import com.example.ticketmaker.Class.User
import com.example.ticketmaker.Common.SharedPrefs
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TicketTemplate : AppCompatActivity() {
    private var prefs : SharedPrefs? = null
    var rowSucursal = ""
    var rowRuta = ""
    var rowVendedor = ""
    var rowFac = ""
    var rowFacID = ""
    var rowRemID = ""
    var rowClienteID = ""
    var rowClienteNombre = ""
    var rowDocumentoID = ""

    var sucurView : TextView? = null
    var rutaView : TextView? = null
    var vendorView : TextView? = null

    var rowFact1 : TextView? = null
    var rowFact1ID : TextView? = null
    var rowRem1 : TextView? = null
    var rowRem1ID : TextView? = null
    var rowDocID : TextView? = null

    var documentoID : TextView? = null
    var clienteID : TextView? = null
    var clienteNombre : TextView? = null

    var iepsTV : TextView? = null
    var iepsValorTV : TextView? = null
    var totalTV : TextView? = null
    var totalIepsTV : TextView? = null
    var subtotalT : TextView? = null
    var subtotalT2 : TextView? = null
    var total2TV : TextView? = null
    var productsQtyTV : TextView? = null
    var productsQtyTotalTV : TextView? = null
    var ventasValorTV : TextView? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var recyclerGroupView: RecyclerView
    private lateinit var viewGroupAdapter: RecyclerView.Adapter<*>
    private lateinit var viewGroupManager: RecyclerView.LayoutManager

    var productsItems : ArrayList<ProductItem>? = null
    var groups : ArrayList<Double>? = null
    var cants : ArrayList<Int>? = null

    var ieps = 0.0
    var total = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_template)
        setSupportActionBar(toolbar)

        prefs = SharedPrefs(baseContext)

        setUpView(prefs!!.getInt(baseContext, "ticket_type", -1))
        fillInfo(prefs!!.getInt(baseContext, "ticket_type", -1))

        /*
        Send back info
         */
        fab.setOnClickListener {
            val view = findViewById<View>(R.id.contentMain)
            val myBitmap = viewToBitmap(view)//v1.drawingCache
            val path = saveToInternalStorage(myBitmap)

            val data = Intent().apply {
                putExtra("path", path)
            }

            Log.e("test", path)
            setResult(Activity.RESULT_OK, data)
            finish()

        }
    }

    fun setUpView(type : Int){
        sucurView = findViewById<View>(R.id.sucursalView) as TextView
        rutaView = findViewById<View>(R.id.rutaView) as TextView
        vendorView = findViewById<View>(R.id.vendedorView) as TextView
        rowFact1 = findViewById<View>(R.id.fac1) as TextView
        rowFact1ID = findViewById<View>(R.id.fac1ID) as TextView
        rowRem1 = findViewById<View>(R.id.rem1) as TextView
        rowRem1ID = findViewById<View>(R.id.rem1ID) as TextView
        rowDocID = findViewById<View>(R.id.documentoID) as TextView
        clienteID = findViewById<View>(R.id.clientID) as TextView
        clienteNombre = findViewById<View>(R.id.clientName) as TextView
        documentoID = findViewById<View>(R.id.documentoID) as TextView

        iepsTV = findViewById<View>(R.id.iepsValue) as TextView
        totalTV = findViewById<View>(R.id.totalSold) as TextView
        ventasValorTV= findViewById<View>(R.id.ventasValor) as TextView
        totalIepsTV = findViewById<View>(R.id.totalPlusIEPS) as TextView
        productsQtyTV= findViewById<View>(R.id.countProducts) as TextView
        productsQtyTotalTV= findViewById<View>(R.id.textView2722) as TextView
        iepsValorTV = findViewById<View>(R.id.IEPSValor) as TextView
        total2TV = findViewById<View>(R.id.IVAValor) as TextView
        subtotalT2 = findViewById<View>(R.id.subtotalT2) as TextView
        subtotalT = findViewById<View>(R.id.subtotalT) as TextView


        if (type == 1){
            rowRem1!!.visibility = View.GONE
            rowRem1ID!!.visibility = View.GONE
        }
        else if (type == 2){
            rowFact1!!.visibility = View.GONE
            rowFact1ID!!.visibility = View.GONE
        }
    }

    fun getIEPDS(total : Double) : String {
        ieps = total* 0.08
        return java.lang.String.format("%.2f", (ieps))
    }


    private fun fillInfo(type : Int){
        val user = User().getLoggedUser(baseContext)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val date = Date()
        rowSucursal = "      SUCURSAL#  ${prefs!!.getString(baseContext, "sucursal").toUpperCase()}               VENDEDOR# ${user.uid.toUpperCase()}"
        rowRuta = "                RUTA#  ${prefs!!.getString(baseContext, "idRuta").toUpperCase()}      ${dateFormat.format(date)}"
        rowVendedor = "VENDEDOR:  ${user.lastName.toUpperCase()} ${user.firstName.toUpperCase()}"
        
        sucurView!!.text = rowSucursal
        rutaView!!.text = rowRuta
        vendorView!!.text = rowVendedor

        when(type){
            1 -> {
                rowFac = "FOLIO FACTURA:    ${prefs!!.getString(baseContext, "factura").toUpperCase()}"
                rowFacID = "FACTURA:            ${prefs!!.getString(baseContext, "folioFactura").toUpperCase()}"

                rowFact1!!.text = rowFac
                rowFact1ID!!.text = rowFacID
            }
            2 -> {
                rowRemID = prefs!!.getString(baseContext, "remision").toUpperCase()
                rowRem1ID!!.text = rowRemID
            }
        }
        rowClienteID = "ID CLIENTE  ${prefs!!.getString(baseContext, "idCliente").toUpperCase()}"
        rowClienteNombre = "NOMBRE:      ${prefs!!.getString(baseContext, "nombreCliente").toUpperCase()}"
        rowDocumentoID = "#DOCUMENTO  ${prefs!!.getString(baseContext, "documento").toUpperCase()}"

        clienteID!!.text = rowClienteID
        clienteNombre!!.text = rowClienteNombre
        documentoID!!.text = rowDocumentoID

        val listType = object : TypeToken<ArrayList<ProductItem>>() {}.type
        productsItems = Gson().fromJson(prefs!!.getString(baseContext, "productsAdded"), listType)

        viewManager = LinearLayoutManager(this)
        viewAdapter = TicketProductAdapter(this, productsItems!!)
        recyclerView = findViewById<RecyclerView>(R.id.p_in_ticket).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }


        val listGroupType = object : TypeToken<ArrayList<Double>>() {}.type
        val listCantType = object : TypeToken<ArrayList<Int>>() {}.type
        groups = Gson().fromJson(prefs!!.getString(baseContext, "groupsAdded"), listGroupType)
        cants = Gson().fromJson(prefs!!.getString(baseContext, "cantsAdded"), listCantType)

        viewGroupManager = LinearLayoutManager(this)
        viewGroupAdapter = GroupsAdapter(this, groups!!, cants!!)
        recyclerGroupView = findViewById<RecyclerView>(R.id.gruposTicket).apply {
            layoutManager = viewGroupManager
            adapter = viewGroupAdapter
        }

        var totalsPrd = 0
        for (i in 0 until productsItems!!.size){
            total += productsItems!![i].subtotal
            totalsPrd += productsItems!![i].cant
        }

        productsQtyTotalTV!!.text = totalsPrd.toString()
        productsQtyTV!!.text = if(productsItems!!.size > 9){
            productsItems!!.size.toString()
        }else{
            "0"+productsItems!!.size
        }
        totalTV!!.text = "+" + java.lang.String.format("%.2f", total)
        ventasValor!!.text = "+" + java.lang.String.format("%.2f", total)
        iepsTV!!.text = getIEPDS(total)
        totalIepsTV!!.text = "+" + java.lang.String.format("%.2f", total + ieps)
        iepsValorTV!!.text = "+" + java.lang.String.format("%.2f",ieps)
        total2TV!!.text = "+" + java.lang.String.format("%.2f", total + ieps)

        subtotalT!!.text = productsItems!!.size.toString() + "  $"
        subtotalT2!!.text = productsItems!!.size.toString() + "  $"


    }

    private fun viewToBitmap(view: View): Bitmap {
        Log.e("Measures w", view.width.toString())
        Log.e("Measures h", view.height.toString())

        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //val returnedBitmap = Bitmap.createScaledBitmap(returnedBitmap1, 219, view.height, false)
        //Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable = view.background
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        // draw the view on the canvas
        view.draw(canvas)
        //return the bitmap
        return returnedBitmap
    }

    private fun saveBitmap(bitmap: Bitmap): String? {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        Log.e("MESSAGE", storageDir!!.toString())
        val image = File.createTempFile(
            "TICKET", /* prefix */
            ".jpeg", /* suffix */
            storageDir      /* directory */
        )
        val imageFilePath = image.absolutePath

        try{
            val fos = FileOutputStream(image)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            Log.d("MESSAGE", "F1 REPORTED SAVE BITMAP")
        }
        catch (e: FileNotFoundException){
            Log.e("GREC", e.message, e)
        }
        catch (e: IOException){
            Log.e("GREC", e.message, e)
        }
        return imageFilePath
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap): String {
        val cw = ContextWrapper(applicationContext)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, "TICKET_TEMPLATE.png")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return mypath.absolutePath
    }

}
