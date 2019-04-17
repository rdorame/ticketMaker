package com.example.ticketmaker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.R

class TicketProcess1 : Fragment(){
    private var prefs : SharedPrefs? = null
    private var rootView: View? = null

    var factura : EditText? = null
    var folioFactura : EditText? = null
    var remision : EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_ticket_process_1, container, false)
        prefs = SharedPrefs(activity!!.baseContext)


        factura = rootView!!.findViewById<View>(R.id.factura) as EditText
        folioFactura = rootView!!.findViewById<View>(R.id.folioFactura) as EditText
        remision = rootView!!.findViewById<View>(R.id.remision) as EditText

        if(activity!!.intent.hasExtra("type")){
            if (activity!!.intent.getIntExtra("type", -1) == 1){
                remision!!.visibility = View.GONE
            }
            else if (activity!!.intent.getIntExtra("type", -1) == 2){
                factura!!.visibility = View.GONE
                folioFactura!!.visibility = View.GONE
            }
        }

        return rootView
    }

    fun next(v : View){

    }
}