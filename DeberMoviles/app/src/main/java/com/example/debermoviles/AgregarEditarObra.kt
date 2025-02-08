package com.example.debermoviles

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityAgregarEditarObra : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var etTituloObra: EditText
    private lateinit var etAnioCreacionObra: EditText
    private lateinit var etTipoObra: EditText
    private lateinit var etPrecioEstimadoObra: EditText
    private lateinit var switchDisponible: Switch
    private lateinit var btnGuardarObra: Button
    private var artistaId: Int = 0
    private var obraId: Int? = null
    private var disponible: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_obra)

        controlador = Controlador(this)
        etTituloObra = findViewById(R.id.etTituloObra)
        etAnioCreacionObra = findViewById(R.id.etAnioCreacionObra)
        etTipoObra = findViewById(R.id.etTipoObra)
        etPrecioEstimadoObra = findViewById(R.id.etPrecioEstimadoObra)
        switchDisponible = findViewById(R.id.switchDisponible)
        btnGuardarObra = findViewById(R.id.btnGuardarObra)
        val tvFormularioTitulo: TextView = findViewById(R.id.tvFormularioTitulo)

        artistaId = intent.getIntExtra("artistaId", 0)
        obraId = intent.getIntExtra("obraId", 0).takeIf { it != 0 }
        switchDisponible.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                disponible = true
                Toast.makeText(this, "Switch activado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Switch desactivado", Toast.LENGTH_SHORT).show()
            }
        }

        // Actualiza el título basado en si estamos editando o creando
        tvFormularioTitulo.text = if (obraId != null) "Editar Obra" else "Agregar Obra"

        // Cargar datos del estudiante si estamos editando
        if (obraId != null) {
            val obra = controlador.listarObrasPorArtista(artistaId).find { it.id == obraId }
            obra?.let {
                etTituloObra.setText(it.titulo)
                etAnioCreacionObra.setText(it.anioCreacion.toString())
                etTipoObra.setText(it.tipoObra)
                etPrecioEstimadoObra.setText(it.precioEstimado.toString())
                switchDisponible.setOnCheckedChangeListener { _, isChecked ->
                    it.disponible = isChecked
                }
            }
        }

        btnGuardarObra.setOnClickListener {
            guardarObra()
        }
    }

    private fun guardarObra() {
        val titulo = etTituloObra.text.toString()
        val anioCreacion = etAnioCreacionObra.text.toString().toIntOrNull()
        val tipoObra = etTipoObra.text.toString()
        val precioEstimado = etPrecioEstimadoObra.text.toString().toDoubleOrNull()
        val disponible = disponible
        if (titulo.isNotEmpty() && anioCreacion != null && tipoObra.isNotEmpty() && precioEstimado != null) {
            if (obraId != null) {
                // Actualizar obra existente
                controlador.actualizarObra(
                    Obra(obraId!!, titulo, anioCreacion, tipoObra, precioEstimado, disponible)
                )
                Toast.makeText(this, "Obra actualizada", Toast.LENGTH_SHORT).show()
            } else {
                // Crear nuevo estudiante
                controlador.crearObra(artistaId, Obra(0, titulo, anioCreacion, tipoObra, precioEstimado, disponible))
                Toast.makeText(this, "Obra creada", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}