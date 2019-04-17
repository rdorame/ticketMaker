package com.example.ticketmaker.Ticket

import android.content.Intent
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.Fragment.TicketProcess1
import com.example.ticketmaker.Fragment.TicketProcess2
import com.example.ticketmaker.MainActivity
import com.example.ticketmaker.SetPrinter
import com.example.ticketmaker.R
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_ticket_process.*

class TicketProcess : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var prefs : SharedPrefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_process)

        prefs = SharedPrefs(baseContext)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            startActivityForResult(Intent(this,  MainActivity::class.java), 100)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_ticket_process, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            val tab1 = TicketProcess1()
            val tab2 = TicketProcess2()

            when (position) {
                0 -> return tab1
                1 -> return tab2
            }
            return null
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
