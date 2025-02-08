package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.CrearTransaccion
import com.example.proyecto.ListTransacciones

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonAddTransaccion = findViewById<Button>(R.id.btn_add_transaccion)
        botonAddTransaccion
            .setOnClickListener {
                irActividad(CrearTransaccion::class.java)
            }

        val botonVerTransacciones = findViewById<Button>(R.id.btn_ver_transacciones)
        botonVerTransacciones
            .setOnClickListener {
                irActividad(ListTransacciones::class.java)
            }

        val botonReportes = findViewById<Button>(R.id.btn_reportes)
        botonAddTransaccion
            .setOnClickListener {
                irActividad(CrearTransaccion::class.java)
            }
    }

    fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }
}