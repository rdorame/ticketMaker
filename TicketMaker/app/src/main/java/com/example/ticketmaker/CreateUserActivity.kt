package com.example.ticketmaker

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.ticketmaker.Class.User

class CreateUserActivity : AppCompatActivity() {
    var userID : EditText? = null
    var uName : EditText? = null
    var uLast : EditText? = null
    var uPass : EditText? = null
    var uPass2 : EditText? = null

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        user = User()
        user!!.cleanUser(baseContext)

        userID = findViewById<View>(R.id._ID) as EditText
        uName = findViewById<View>(R.id._name) as EditText
        uLast = findViewById<View>(R.id._lName) as EditText
        uPass = findViewById<View>(R.id._password) as EditText
        uPass2 = findViewById<View>(R.id._repeatPassword) as EditText
    }

    fun saveUser (view : View){
        if (validatePassword()) {
            user = User(userID!!.text.toString(), uName!!.text.toString(), uLast!!.text.toString(), uPass!!.text.toString())
            user!!.saveUser(baseContext)
            setResult(Activity.RESULT_OK)
            finish()
        }
        else{
            Toast.makeText(baseContext, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validatePassword() : Boolean{
        if(uPass!!.text.toString() == uPass2!!.text.toString())
            return true
        return false
    }
}
