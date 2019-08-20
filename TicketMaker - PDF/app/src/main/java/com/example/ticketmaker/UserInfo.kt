package com.example.ticketmaker

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ticketmaker.Class.User

class UserInfo : AppCompatActivity() {
    var userID : EditText? = null
    var uName : EditText? = null
    var uLast : EditText? = null

    var user : User? = null
    var currentUser : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        currentUser = User().getLoggedUser(baseContext)

        userID = findViewById<View>(R.id._ID) as EditText
        uName = findViewById<View>(R.id._name) as EditText
        uLast = findViewById<View>(R.id._lName) as EditText

        userID!!.setText(currentUser!!.uid, TextView.BufferType.EDITABLE)
        uName!!.setText(currentUser!!.firstName, TextView.BufferType.EDITABLE)
        uLast!!.setText(currentUser!!.lastName, TextView.BufferType.EDITABLE)
    }

    fun saveUser (view : View){
        user = User(userID!!.text.toString(), uName!!.text.toString(), uLast!!.text.toString(), currentUser!!.password)
        user!!.saveUser(baseContext)
        setResult(Activity.RESULT_OK)
        finish()
    }

}
