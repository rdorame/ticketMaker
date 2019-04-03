package com.example.ticketmaker.Common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.ticketmaker.LoginActivity
import com.example.ticketmaker.R
import com.example.ticketmaker.TicketList

class LaunchScreen : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000
    private var prefs : SharedPrefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)
        prefs = SharedPrefs(baseContext)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val session = prefs!!.getString(baseContext, "SESSION", "NotLogged")
            if (session == "Logged") {
                startActivity(Intent(this@LaunchScreen, TicketList::class.java))
            } else {
                startActivity(Intent(this@LaunchScreen, LoginActivity::class.java))
            }
            finish()
        }
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}