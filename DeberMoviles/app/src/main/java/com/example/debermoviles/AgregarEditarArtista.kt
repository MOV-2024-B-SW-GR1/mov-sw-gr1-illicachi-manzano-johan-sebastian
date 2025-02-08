package com.example.debermoviles

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityAgregarEditarArtista : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var etNombreArtista: EditText
    private lateinit var etEdadArtista: EditText
    private lateinit var etNacionalidadArtista: EditText
    private lateinit var etFechaNacimientoArtista: EditText
    private lateinit var etLatitudArtista: EditText
    private lateinit var etAltitudArtista: EditText
    private lateinit var btnGuardarArtista: Button
    private var artistaId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_artista)

        controlador = Controlador(this)
        etNombreArtista = findViewById(R.id.etNombreArtista)
        etEdadArtista = findViewById(R.id.etEdadArtista)
        etNacionalidadArtista = findViewById(R.id.etNacionalidadArtista)
        etFechaNacimientoArtista = findViewById(R.id.etFechaNacimientoArtista)
        etLatitudArtista = findViewById(R.id.etLatitud)
        etAltitudArtista = findViewById(R.id.etAltitud)
        btnGuardarArtista = findViewById(R.id.btnGuardarArtista)
        val tvFormularioTitulo: TextView = findViewById(R.id.tvFormularioTitulo)
        tvFormularioTitulo.text = if (artistaId != null) "Editar Artista" else "Agregar Artista"

        artistaId = intent.getIntExtra("artistaId", 0).takeIf { it != 0 }

        if (artistaId != null) {
            val artista = controlador.listarArtistas().find { it.id == artistaId }
            artista?.let {
                etNombreArtista.setText(it.nombre)
                etEdadArtista.setText(it.edad.toString())
                etNacionalidadArtista.setText(it.nacionalidad)
                etFechaNacimientoArtista.setText(it.fechaNacimiento.toString())
                etLatitudArtista.setText(it.latitud.toString())
                etAltitudArtista.setText(it.altitud.toString())


            }
        }

        btnGuardarArtista.setOnClickListener {
            val nombre = etNombreArtista.text.toString()
            val edad = etEdadArtista.text.toString().toIntOrNull()
            val nacionalidad = etNacionalidadArtista.text.toString()
            val fechaNacimiento = etFechaNacimientoArtista.text.toString()
            val latitud = etLatitudArtista.text.toString().toDoubleOrNull()
            val altitud = etAltitudArtista.text.toString().toDoubleOrNull()


            if (nombre.isNotEmpty() && edad != null && nacionalidad.isNotEmpty() && fechaNacimiento.isNotEmpty() && latitud != null && altitud != null) {
                if (artistaId != null) {
                    controlador.actualizarArtista(Artista(artistaId!!, nombre, edad, nacionalidad, fechaNacimiento,latitud, altitud))
                    Toast.makeText(this, "Artista actualizado", Toast.LENGTH_SHORT).show()
                } else {
                    val nuevoId = (controlador.listarArtistas().maxOfOrNull { it.id } ?: 0) + 1
                    controlador.crearArtista(Artista(nuevoId, nombre, edad, nacionalidad, fechaNacimiento, latitud, altitud))
                    Toast.makeText(this, "Artista creado", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}