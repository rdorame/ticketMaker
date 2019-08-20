package com.example.ticketmaker.Common

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import com.example.ticketmaker.LoginActivity
import com.example.ticketmaker.Ticket.TicketList
import android.Manifest.permission.READ_PHONE_STATE
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.Toast
import com.example.ticketmaker.MainActivity
import android.hardware.usb.UsbDevice.getDeviceId
import android.Manifest.permission
import android.Manifest.permission.READ_PHONE_STATE
import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LaunchScreen : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000
    private var prefs : SharedPrefs? = null
    var telephonyManager: TelephonyManager? = null
    var imeiCompare = (0).toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.ticketmaker.R.layout.activity_launch_screen)
        prefs = SharedPrefs(baseContext)



        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
        }

        val imeiArrayList = ArrayList<Long>()
        imeiArrayList.add(357086101750139)
        imeiArrayList.add(355636084351222)
        imeiArrayList.add(351864089006435)
        imeiArrayList.add(351843091775708)
        imeiArrayList.add(355924101415661)
        imeiArrayList.add(354259072308996)

        imeiArrayList.add(867904030534494)
        imeiArrayList.add(355636084017591)
        imeiArrayList.add(356263090309489)
        imeiArrayList.add(351843096915416)
        imeiArrayList.add(863705040970519)
        imeiArrayList.add(867259020415877)
        imeiArrayList.add(866225030522797)
        imeiArrayList.add(351843096915416)
        imeiArrayList.add(358240051111110) //Emulator

        Log.i("IMEI", prefs!!.getLong(baseContext, "IMEI", -1).toString())

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        var date = Date()
        var todat = sdf.format(date)
        var toCompareDate = sdf.parse("2030-07-15")
        var lastDate = sdf.format(toCompareDate)

        Log.i("TAG", "$todat   $toCompareDate")
        if (todat < lastDate) {
            if(prefs!!.getLong(baseContext, "IMEI", -1) != (-1).toLong()){
                imeiCompare = prefs!!.getLong(baseContext, "IMEI", -1)
                Log.e("IMEI", imeiCompare.toString())
            }
            else {
                Log.e("IMEI", "ENTERING")
                deviceId()
                imeiCompare = prefs!!.getLong(baseContext, "IMEI", -1)
                Log.e("IMEI", imeiCompare.toString())
                Log.e("IMEI", "EXIT")
            }
            if (ActivityCompat.checkSelfPermission(
                    this,
                    READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.i("IMEI", "ENTERING PERMISSION")
                ActivityCompat.requestPermissions(this, arrayOf(READ_PHONE_STATE), 101)
                return
            }



            if (imeiArrayList.contains(imeiCompare)){
                //Initialize the Handler
                mDelayHandler = Handler()

                //Navigate with delay
                mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
            }
            else{
                Toast.makeText(baseContext, "Lo sentimos, IMEI no agregado", Toast.LENGTH_LONG).show()
                /* //
                 //Initialize the Handler
                 mDelayHandler = Handler()

                 //Navigate with delay
                 mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)*/
            }
        }
        else{
            Toast.makeText(baseContext, "Contacte al administrador de la app, error mayor al iniciar la app", Toast.LENGTH_LONG).show()
        }


    }

    private fun deviceId() {
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(
                this,
                READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("IMEI", "ENTERING PERMISSION")
            ActivityCompat.requestPermissions(this, arrayOf(READ_PHONE_STATE), 101)
            return
        }
        else{
            imeiCompare = telephonyManager!!.deviceId.toLong()
            prefs!!.save(baseContext, "IMEI",  imeiCompare)
            Log.e("IMEI", imeiCompare.toString())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        READ_PHONE_STATE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this, arrayOf(READ_PHONE_STATE), 101)
                    return
                }
                imeiCompare = telephonyManager!!.deviceId.toLong()
                prefs!!.save(baseContext, "IMEI",  imeiCompare)
                Log.e("IMEI", imeiCompare.toString())
                //Toast.makeText(this@LaunchScreen, IMEI, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@LaunchScreen, "Without permission we check", Toast.LENGTH_LONG).show()
                Log.e("IMEI", "ERROR")
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
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