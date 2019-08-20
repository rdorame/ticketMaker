package com.example.ticketmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.ticketmaker.Class.User
import com.example.ticketmaker.Common.SharedPrefs
import com.example.ticketmaker.Ticket.TicketList
import kotlinx.android.synthetic.main.activity_ticket_list.*

class LoginActivity : AppCompatActivity() {

    var prefs : SharedPrefs? = null
    var idInput : EditText? = null
    var passInput : EditText? = null
    var result = false
    var loggedUser : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        prefs = SharedPrefs(baseContext)
        idInput = findViewById<View>(R.id._ID) as EditText
        passInput = findViewById<View>(R.id._pass) as EditText
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            loggedUser = User().getLoggedUser(baseContext)
        }
    }

    fun doLogin(v : View){
        if (validateUser()){
            Toast.makeText(baseContext, "Bienvenido", Toast.LENGTH_SHORT).show()
            val intent : Intent = Intent(this@LoginActivity, TicketList::class.java)
            startActivity(intent)
        }
    }

    private fun validateUser() : Boolean{
        loggedUser = User().getLoggedUser(baseContext)
        Log.d("User", "ID: ${loggedUser!!.uid}   Pass: ${loggedUser!!.password}")

        if(loggedUser!!.uid != "" && loggedUser!!.password != "") {
            if(idInput!!.text.toString() == "" || passInput!!.text.toString() == ""){
                Toast.makeText(baseContext, "Por favor ingresa tus credenciales", Toast.LENGTH_SHORT).show()
            }else{
                if(idInput!!.text.toString() == loggedUser!!.uid && passInput!!.text.toString() == loggedUser!!.password){
                    prefs!!.save(baseContext, "SESSION", "Logged")
                    return true
                }
                else{
                    Toast.makeText(baseContext, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            Toast.makeText(baseContext, "No hay usuarios registrados", Toast.LENGTH_SHORT).show()
        }

        return false
    }

    fun newUser(view : View) {
        val builder = AlertDialog.Builder(this)

        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT

        // Set the alert dialog title
        builder.setTitle("Validar")
        builder.setMessage("Ingresa el código de seguridad que te fue proporcionado")
        builder.setView(input)
        // Display a neutral button on alert dialog
        builder.setPositiveButton("Continuar") { _, _ ->
            if (input.text.toString() == "9455") {
                Toast.makeText(baseContext, "Código correcto", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CreateUserActivity::class.java)
                startActivityForResult(intent, 100)
            }
            else{
                Toast.makeText(baseContext, "El código ingresado no es el correcto", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { _, _ ->

        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
}
