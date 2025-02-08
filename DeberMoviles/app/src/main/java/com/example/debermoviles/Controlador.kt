package com.example.debermoviles

import android.content.ContentValues
import android.content.Context

class Controlador (context: Context) {
    private val dbHelper = SqliteHelper(context)

    //Crear Artista
    fun crearArtista(artista: Artista) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("id", artista.id)
            put("nombre", artista.nombre)
            put("edad", artista.edad)
            put("nacionalidad", artista.nacionalidad)
            put("fecha_nacimiento", artista.fechaNacimiento)
        }
        db.insert("Artista", null, valores)
        db.close()
    }

    //Listar Artista
    fun listarArtistas(): List<Artista> {
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM Artista", null)
        val artistas = mutableListOf<Artista>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val edad = cursor.getInt(2)
            val nacionalidad = cursor.getString(3)
            val fechaNacimiento = cursor.getString(5)

            artistas.add(Artista(id, nombre, edad, nacionalidad, fechaNacimiento))
        }
        cursor.close()
        db.close()
        return artistas
    }

    //Actualizar Artista
    fun actualizarArtista(artista: Artista): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", artista.nombre)
            put("edad", artista.edad)
            put("nacionalidad", artista.nacionalidad)
            put("fecha_nacimiento", artista.fechaNacimiento)

        }
        val rows = db.update(
            "Artista",
            valores,
            "id = ?",
            arrayOf(artista.id.toString())
        )
        db.close()
        return rows > 0
    }

    //Eliminar Artista
    fun eliminarArtista (ArtistaId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(
            "Artista",
            "id = ?",
            arrayOf(ArtistaId.toString())
        )
        db.close()
        return rows > 0
    }

    //Crear Obra
    fun crearObra(artistaId: Int, obra: Obra) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("titulo", obra.titulo)
            put("anio_creacion", obra.anioCreacion)
            put("tipo_obra", obra.tipoObra)
            put("precio_estimado", obra.precioEstimado)
            put("disponible", obra.disponible)
            put("artista_id", artistaId)
        }
        db.insert("Obra", null, valores)
        db.close()
    }

    //Listar obras de un artista
    fun listarObrasPorArtista(artistaId: Int): List<Obra> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM Obra WHERE artista_id = ?", // Actualizado
            arrayOf(artistaId.toString())
        )
        val obras = mutableListOf<Obra>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val anioCreacion = cursor.getInt(cursor.getColumnIndexOrThrow("anio_creacion"))
            val tipoObra = cursor.getString(cursor.getColumnIndexOrThrow("tipo_obra"))
            val precioEstimado = cursor.getDouble(cursor.getColumnIndexOrThrow("precio_estimado"))
            val disponible = cursor.getInt(cursor.getColumnIndexOrThrow("disponible")) != 0
            obras.add(Obra(id, titulo, anioCreacion, tipoObra, precioEstimado, disponible))
        }
        cursor.close()
        db.close()
        return obras
    }

    //Actualizar Obras
    fun actualizarObra(obra: Obra): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("titulo", obra.titulo)
            put("anio_creacion", obra.anioCreacion)
            put("tipo_obra", obra.tipoObra)
            put("precio_estimado", obra.precioEstimado)
            put("disponible", obra.disponible)
        }
        val rows = db.update("Obra", values, "id = ?", arrayOf(obra.id.toString()))
        db.close()
        return rows > 0
    }

    // Eliminar Obra
    fun eliminarObra(obraId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete("Obra", "id = ?", arrayOf(obraId.toString()))
        db.close()
        return rows > 0
    }

    fun depurarObras() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Obra", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val cursoId = cursor.getInt(cursor.getColumnIndexOrThrow("artista_id"))
            println("Estudiante: $id, Nombre: $titulo, Curso ID: $cursoId")
        }
        cursor.close()
        db.close()
    }

}