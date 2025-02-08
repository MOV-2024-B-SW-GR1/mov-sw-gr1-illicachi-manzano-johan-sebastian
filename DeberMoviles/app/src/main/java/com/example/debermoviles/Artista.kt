package com.example.debermoviles

class Artista(
    val id: Int,
    var nombre: String,
    var edad: Int,
    var nacionalidad: String,
    var fechaNacimiento: String,
    var latitud: Double = 0.0,
    var altitud: Double = 0.0,
    var obras: MutableList<Obra> = mutableListOf()
)
