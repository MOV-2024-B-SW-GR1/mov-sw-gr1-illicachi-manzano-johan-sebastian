package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val respuestaLoginUiAuth = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
            res: FirebaseAuthUIAuthenticationResult ->
        if(res.resultCode == RESULT_OK){
            if(res.idpResponse != null){
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(res: IdpResponse){
        irActividad(Inicio::class.java)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        //Inicializar Base de datos
        BaseDeDatos.tablatransaccion = SqliteHelper(this)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            val logearseIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers).build()
            respuestaLoginUiAuth.launch(logearseIntent)
        }

        val usuario = FirebaseAuth.getInstance().currentUser

    }

    fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }
}