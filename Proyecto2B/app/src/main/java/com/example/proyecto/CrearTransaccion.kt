package com.example.proyecto

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.BaseDeDatos
import com.example.proyecto.Inicio
import com.google.android.material.snackbar.Snackbar
import java.time.format.DateTimeFormatter

class CrearTransaccion : AppCompatActivity() {

    var opcionSeleccionada = ""
    var tipo = ""
    var opciones = listOf("Seleccione un tipo")

    fun mostrarSnackbar(texto: String){
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_transaccion)

        val spinner: Spinner = findViewById(R.id.id_spinner)
        val inputMonto = findViewById<EditText>(R.id.input_monto)
        val inputDescripcion = findViewById<EditText>(R.id.input_descripcion)
        val botonCrearTransaccion = findViewById<Button>(R.id.btn_guardar)

        // Recibir datos si existen (modo edición)
        val id = intent.getIntExtra("id", -1)
        val fecha = intent.getStringExtra("fecha")
        val monto = intent.getDoubleExtra("monto", 0.0)
        val tipo = intent.getStringExtra("tipo")
        val categoria = intent.getStringExtra("categoria")
        val descripcion = intent.getStringExtra("descripcion")

        val botonTipoGasto = findViewById<Button>(R.id.btn_gasto)
        botonTipoGasto
            .setOnClickListener {
                this.tipo = "gasto"
                opciones = listOf("Servicios Básicos", "Alimentación", "Entretenimiento", "Transporte")
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        val botonTipoIngreso = findViewById<Button>(R.id.btn_ingreso)
        botonTipoIngreso
            .setOnClickListener {
                this.tipo = "ingreso"
                opciones = listOf("Salario ", "Regalías", "Subsidios", "Ingreso por venta", "Prestamo ")
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        if (id != -1) { // Si viene con un ID, es modo edición
            inputMonto.setText(monto.toString())
            inputDescripcion.setText(descripcion)

            this.tipo = tipo ?: ""
            opciones = if (tipo == "gasto") {
                listOf("Servicios Básicos", "Alimentación", "Entretenimiento", "Transporte", "Entretenimiento ")
            } else {
                listOf("Salario ", "Regalías", "Subsidios", "Ingreso por venta", "Préstamo ")
            }

            // Configurar el spinner con la categoría recibida
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setSelection(opciones.indexOf(categoria ?: ""))

            botonCrearTransaccion.text = "Actualizar"
        }

        botonCrearTransaccion.setOnClickListener {
            val nuevaFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy").toString()
            val nuevoMonto = inputMonto.text.toString().toDouble()
            val nuevaCategoria = opciones[spinner.selectedItemPosition]
            val nuevaDescripcion = inputDescripcion.text.toString()

            if (id == -1) { // Crear nueva transacción
                val respuesta = BaseDeDatos.tablatransaccion!!
                    .crearTransaccion(nuevaFecha, nuevoMonto, this.tipo, nuevaCategoria, nuevaDescripcion)
                if (respuesta) mostrarSnackbar("Transacción creada") else mostrarSnackbar("Fallo al crear")
            } else { // Editar transacción existente
                val respuesta = BaseDeDatos.tablatransaccion!!
                    .actualizarTransaccion(id, nuevaFecha, nuevoMonto, this.tipo, nuevaCategoria, nuevaDescripcion)
                if (respuesta) mostrarSnackbar("Transacción actualizada") else mostrarSnackbar("Fallo al actualizar")
            }

            irActividad(Inicio::class.java)
        }
    }

    fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }


}
