package mx.itesm.perdafirulais.autenticacion

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import mx.itesm.perdafirulais.Info
import mx.itesm.perdafirulais.MainMenu
import mx.itesm.perdafirulais.R

class LoginActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private var tag = "_Login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnRegistro.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        mAuth = FirebaseAuth.getInstance()
        btnIngresar.setOnClickListener {
            Log.d(tag, "Intentando Logear")
            manejarLogin()
        }

        btnInfo.setOnClickListener {
            val intent = Intent(this, Info::class.java)
            startActivity(intent)
        }


        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }


    private fun manejarLogin() {
        val mail = etMail.text.toString()
        val password = etPassword.text.toString()
        if (mail.isNullOrBlank() || password.isNullOrBlank()) {
            Toast.makeText(
                this, "Falta llenar alguno de los campos.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            login(mail, password)
        }
    }

    private fun login(mail: String, password: String) {
        mAuth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(tag, "signInWithEmail:success")
                    Toast.makeText(
                        this, "Login Satisfactorio.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainMenu::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        this, "No se pudo iniciar sesion. ${task.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


}
