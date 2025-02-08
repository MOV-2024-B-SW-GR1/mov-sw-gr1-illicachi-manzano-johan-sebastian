package com.example.debermoviles

class Artista (
    val id: Int,
    var nombre: String,
    var edad: Int,
    var nacionalidad: String,
    var fechaNacimiento: String,
    var obras: MutableList<Obra> = mutableListOf()
)