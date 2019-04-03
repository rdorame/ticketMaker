package com.example.ticketmaker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.R

class TicketProcess2: Fragment() {

    private var prefs : SharedPrefs? = null
    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_ticket_process_2, container, false)
        prefs = SharedPrefs(activity!!.baseContext)

        return rootView
    }
}