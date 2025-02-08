package com.example.debermoviles

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityArtistaList : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var listViewArtistas: ListView
    private lateinit var btnAgregarArtista: Button
    private var selectedArtista: Artista? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artista_list)

        controlador = Controlador(this)
        listViewArtistas = findViewById(R.id.listViewArtistas)
        btnAgregarArtista = findViewById(R.id.btnAgregarArtista)

        btnAgregarArtista.setOnClickListener {
            val intent = Intent(this, ActivityAgregarEditarArtista::class.java)
            startActivity(intent)
        }

        registerForContextMenu(listViewArtistas)
        actualizarLista()
    }

    private fun actualizarLista() {
        val artistas = controlador.listarArtistas()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, artistas.map { it.nombre })
        listViewArtistas.adapter = adapter

        listViewArtistas.setOnItemClickListener { _, _, position, _ ->
            selectedArtista = controlador.listarArtistas()[position]
            listViewArtistas.showContextMenu()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_artista, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuVerObras -> {
                val intent = Intent(this, ActivityObraList::class.java)
                intent.putExtra("artistaId", selectedArtista?.id)
                startActivity(intent)
            }
            R.id.menuEditarArtista -> {
                val intent = Intent(this, ActivityAgregarEditarArtista::class.java)
                intent.putExtra("artistaId", selectedArtista?.id)
                startActivity(intent)
            }
            R.id.menuEliminarArtista -> {
                if (selectedArtista != null) {
                    controlador.eliminarArtista(selectedArtista!!.id)
                    Toast.makeText(this, "Artista eliminado", Toast.LENGTH_SHORT).show()
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