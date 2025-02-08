package com.example.debermoviles


import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityObraList : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var tvArtistaTitulo: TextView
    private lateinit var listViewObras: ListView
    private lateinit var btnAgregarObra: Button
    private var artistaId: Int = 0
    private var selectedObra: Obra? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obra_list)

        controlador = Controlador(this)
        tvArtistaTitulo = findViewById(R.id.tvArtistaTitulo)
        listViewObras = findViewById(R.id.listViewObras)
        btnAgregarObra = findViewById(R.id.btnAgregarObra)

        // Obtener el ID del artista
        artistaId = intent.getIntExtra("artistaId", 0)

        // Mostrar el nombre del Artista en el título
        val artista = controlador.listarArtistas().find { it.id == artistaId }
        if (artista != null) {
            tvArtistaTitulo.text = artista.nombre
        } else {
            tvArtistaTitulo.text = "Artista no encontrado"
        }

        btnAgregarObra.setOnClickListener {
            val intent = Intent(this, ActivityAgregarEditarObra::class.java)
            intent.putExtra("artistaId", artistaId)
            startActivity(intent)
        }

        registerForContextMenu(listViewObras)
        actualizarLista()
    }

    private fun actualizarLista() {
        val obras = controlador.listarObrasPorArtista(artistaId)
        if (obras.isEmpty()) {
            Toast.makeText(this, "No hay obras de este artista", Toast.LENGTH_SHORT).show()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, obras.map { it.titulo })
        listViewObras.adapter = adapter

        listViewObras.setOnItemLongClickListener { _, _, position, _ ->
            selectedObra = obras[position]
            listViewObras.showContextMenu()
            //false // Esto permite que el menú contextual se muestre
        }
    }



    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_obra, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuEditarObra -> {
                val intent = Intent(this, ActivityAgregarEditarObra::class.java)
                intent.putExtra("artistaId", artistaId)
                intent.putExtra("obraId", selectedObra?.id)
                startActivity(intent)
            }
            R.id.menuEliminarObra -> {
                if (selectedObra != null) {
                    controlador.eliminarObra(selectedObra!!.id)
                    Toast.makeText(this, "Obra eliminada", Toast.LENGTH_SHORT).show()
                    actualizarLista()
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        actualizarLista()
    }

}