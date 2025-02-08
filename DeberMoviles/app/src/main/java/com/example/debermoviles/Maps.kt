package com.example.debermoviles

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class Maps : AppCompatActivity() {

    private val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION

    private lateinit var mapa: GoogleMap
    private lateinit var tvArtistaTitulo: TextView

    private var artistaId: Int = 0
    private var nombreArtista: String? = null
    private var latitud: Double = 0.0
    private var altitud: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        tvArtistaTitulo = findViewById(R.id.tv_ubicacion_nombre_artista)

        // Obtener datos del artista desde el Intent
        artistaId = intent.getIntExtra("artistaId", 0)
        nombreArtista = intent.getStringExtra("nombre")
        latitud = intent.getDoubleExtra("latitud", 0.0)
        altitud = intent.getDoubleExtra("altitud", 0.0)

        // Mostrar el nombre del artista
        tvArtistaTitulo.text = nombreArtista ?: "Artista no encontrado"

        solicitarPermisos()
        inicializarLogicaMapa()
    }

    private fun solicitarPermisos() {
        if (!tengoPermisos()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    private fun tengoPermisos(): Boolean {
        val permisoFine = ContextCompat.checkSelfPermission(this, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(this, nombrePermisoCoarse)
        return permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
    }

    private fun inicializarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            mapa = googleMap
            establecerConfiguracionMapa()
            mostrarUbicacionArtista()
        }
    }

    private fun mostrarUbicacionArtista() {
        if (latitud != 0.0 && altitud != 0.0) {
            val ubicacionArtista = LatLng(latitud, altitud)
            mapa.addMarker(
                MarkerOptions()
                    .position(ubicacionArtista)
                    .title(nombreArtista)
            )
            moverCamaraConZoom(ubicacionArtista)
        } else {
            mostrarSnackbar("Ubicación del artista no disponible")
        }
    }

    @SuppressLint("MissingPermission")
    private fun establecerConfiguracionMapa() {
        with(mapa) {
            if (tengoPermisos()) {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 15f) {
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(findViewById(R.id.main), texto, Snackbar.LENGTH_LONG).show()
    }
}
