package com.example.ticketmaker

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.ticketmaker.Ticket.TicketTemplate
import com.example.ticketmaker.Utils.Conts
import com.example.ticketmaker.Utils.DeviceReceiver
import com.google.android.material.snackbar.Snackbar

import net.posprinter.posprinterface.IMyBinder
import net.posprinter.posprinterface.UiExecute
import net.posprinter.service.PosprinterService
import net.posprinter.utils.DataForSendToPrinterPos80
import net.posprinter.utils.PosPrinterDev

import java.util.ArrayList

class SetPrinter : AppCompatActivity(), View.OnClickListener {
    var DISCONNECT = "com.posconsend.net.disconnetct"

    //IMyBinder interface，All methods that can be invoked to connect and send data are encapsulated within this interface
    lateinit var binder: IMyBinder
    //bindService connection
    var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            //Bind successfully
            binder = iBinder as IMyBinder
            Log.d("binder", "connected")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.d("disbinder", "disconnected")
        }
    }

    var ISCONNECT: Boolean = false
    internal lateinit var BTCon: Button//connection button
    private lateinit var BTDisconnect: Button//disconnect button
    private lateinit var BTpos: Button
    private lateinit var BtSb: Button// start posprint button
    private lateinit var conPort: Spinner//spinner connetion port
    internal lateinit var showET: EditText// show edittext
    private lateinit var container: CoordinatorLayout
    private var dialogView: View? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var adapter1: ArrayAdapter<String>? = null
    private var adapter2: ArrayAdapter<String>? = null
    private var lv1: ListView? = null
    private var lv2: ListView? = null
    private var lv_usb: ListView? = null
    private val deviceList_bonded = ArrayList<String>()//bonded list
    private val deviceList_found = ArrayList<String>()//found list
    private var btn_scan: Button? = null //scan button
    private var LLlayout: LinearLayout? = null
    private lateinit var dialog: AlertDialog
    private lateinit var mac: String
    internal var pos: Int = 0

    private var myDevice: DeviceReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)


        //bind service，get ImyBinder object
        val intent = Intent(this, PosprinterService::class.java)
        bindService(intent, conn, BIND_AUTO_CREATE)
        //init view
        initView()

        //setlistener
        setlistener()
    }

    private fun initView() {

        BTCon = findViewById<View>(R.id.buttonConnect) as Button
        BTDisconnect = findViewById<View>(R.id.buttonDisconnect) as Button

        BTpos = findViewById<View>(R.id.buttonpos) as Button

        BtSb = findViewById<View>(R.id.buttonSB) as Button
        conPort = findViewById<View>(R.id.connectport) as Spinner
        showET = findViewById<View>(R.id.showET) as EditText
        container = findViewById<View>(R.id.container) as CoordinatorLayout
    }

    private fun setlistener() {
        BTCon.setOnClickListener(this)
        BTDisconnect.setOnClickListener(this)

        BTpos.setOnClickListener(this)

        BtSb.setOnClickListener(this)
        conPort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                pos = i
                when (i) {
                    1 -> {
                        //bluetooth connect
                        showET.setText("")
                        BtSb.visibility = View.VISIBLE
                        showET.hint = getString(R.string.bleselect)
                        showET.isEnabled = false
                    }
                    else -> {
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

    }

    override fun onClick(view: View) {

        val id = view.id
        //connect button
        if (id == R.id.buttonConnect) {
            when (pos) {
                1 ->
                    //bluetooth connection
                    connetBle()
            }
        }
        //device button
        if (id == R.id.buttonSB) {
            when (pos) {
                0 -> BTCon.text = getString(R.string.connect)
                1 -> {
                    setBluetooth()
                    BTCon.text = getString(R.string.connect)
                }
            }

        }
        //disconnect
        if (id == R.id.buttonDisconnect) {
            if (ISCONNECT) {
                binder.disconnectCurrentPort(object : UiExecute {
                    override fun onsucess() {
                        //showSnackbar(getString(R.string.toast_discon_success))
                        showET.setText("")
                        BTCon.text = getString(R.string.connect)
                    }

                    override fun onfailed() {
                        showSnackbar(getString(R.string.toast_discon_faile))

                    }
                })
            } else {
                showSnackbar(getString(R.string.toast_present_con))
            }
        }
        //start to pos printer
        if (id == R.id.buttonpos) {
            if (ISCONNECT) {
                val intent = Intent(this, TicketTemplate::class.java)
                intent.putExtra("isconnect", ISCONNECT)
                startActivity(intent)
            } else {
                showSnackbar(getString(R.string.connect_first))
            }

        }

    }

    /*
    bluetooth connecttion
     */
    private fun connetBle() {
        val bleAdrress = showET.text.toString()
        if (bleAdrress == "") {
            showSnackbar(getString(R.string.bleselect))
        } else {
            binder.connectBtPort(bleAdrress, object : UiExecute {
                override fun onsucess() {
                    ISCONNECT = true
                    showSnackbar(getString(R.string.con_success))
                    BTCon.text = getString(R.string.con_success)

                    binder.write(DataForSendToPrinterPos80.openOrCloseAutoReturnPrintState(0x1f), object : UiExecute {
                        override fun onsucess() {
                            binder.acceptdatafromprinter(object : UiExecute {
                                override fun onsucess() {

                                }

                                override fun onfailed() {
                                    ISCONNECT = false
                                    showSnackbar(getString(R.string.con_has_discon))
                                }
                            })
                        }

                        override fun onfailed() {

                        }
                    })


                }

                override fun onfailed() {

                    ISCONNECT = false
                    showSnackbar(getString(R.string.con_failed))
                }
            })
        }


    }

    fun setBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (!bluetoothAdapter!!.isEnabled) {
            //open bluetooth
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, Conts.ENABLE_BLUETOOTH)
        } else {

            showblueboothlist()

        }
    }

    private fun showblueboothlist() {
        if (!bluetoothAdapter!!.isDiscovering) {
            bluetoothAdapter!!.startDiscovery()
        }
        val inflater = LayoutInflater.from(this)
        dialogView = inflater.inflate(R.layout.printer_list, null)
        adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceList_bonded)
        lv1 = dialogView!!.findViewById(R.id.listView1) as ListView
        btn_scan = dialogView!!.findViewById(R.id.btn_scan) as Button
        LLlayout = dialogView!!.findViewById(R.id.ll1) as LinearLayout
        lv2 = dialogView!!.findViewById(R.id.listView2) as ListView
        adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceList_found)
        lv1!!.adapter = adapter1
        lv2!!.adapter = adapter2
        dialog = AlertDialog.Builder(this).setTitle("BLE").setView(dialogView).create()
        dialog.show()

        myDevice = DeviceReceiver(deviceList_found, adapter2, lv2)

        //register the receiver
        val filterStart = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val filterEnd = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(myDevice, filterStart)
        registerReceiver(myDevice, filterEnd)

        setDlistener()
        findAvalibleDevice()
    }

    private fun setDlistener() {
        btn_scan!!.setOnClickListener {
            LLlayout!!.visibility = View.VISIBLE
            //btn_scan.setVisibility(View.GONE);
        }
        //boned device connect
        lv1!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, arg2, _ ->
            try {
                if (bluetoothAdapter != null && bluetoothAdapter!!.isDiscovering) {
                    bluetoothAdapter!!.cancelDiscovery()
                }

                val msg = deviceList_bonded[arg2]
                mac = msg.substring(msg.length - 17)
                val name = msg.substring(0, msg.length - 18)
                //lv1.setSelection(arg2);
                dialog.cancel()
                showET.setText(mac)
                //Log.i("TAG", "mac="+mac);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        //found device and connect device
        lv2!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, arg2, _ ->
            Log.d("Click", "Click")
            try {
                if (bluetoothAdapter != null && bluetoothAdapter!!.isDiscovering) {
                    bluetoothAdapter!!.cancelDiscovery()

                }
                val msg = deviceList_found[arg2]
                mac = msg.substring(msg.length - 17)
                val name = msg.substring(0, msg.length - 18)
                //lv2.setSelection(arg2);
                dialog.cancel()
                showET.setText(mac)
                Log.i("TAG", "mac=$mac")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun findAvalibleDevice() {

        val device = bluetoothAdapter!!.bondedDevices

        deviceList_bonded.clear()
        if (bluetoothAdapter != null && bluetoothAdapter!!.isDiscovering) {
            adapter1!!.notifyDataSetChanged()
        }
        if (device.size > 0) {
            //already
            val it = device.iterator()
            while (it.hasNext()) {
                val btd = it.next()
                deviceList_bonded.add(btd.name + '\n'.toString() + btd.address)
                adapter1!!.notifyDataSetChanged()
            }
        } else {
            deviceList_bonded.add("No can be matched to use bluetooth")
            adapter1!!.notifyDataSetChanged()
        }

    }

    fun showSnackbar(showstring: String) {
        Snackbar.make(container, showstring, Snackbar.LENGTH_LONG)
            .setActionTextColor(resources.getColor(R.color.button_unable)).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binder.disconnectCurrentPort(object : UiExecute {
            override fun onsucess() {

            }

            override fun onfailed() {

            }
        })
        unbindService(conn)
    }

}
