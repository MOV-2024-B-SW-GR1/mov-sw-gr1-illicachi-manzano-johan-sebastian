package com.example.debermoviles

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context?) : SQLiteOpenHelper(
    context,
    "ArtistasDB", // Nombre de la base de datos
    null,
    1 // Versión inicial
) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Crear tabla Artista
        val scriptSqlCrearArtista = """
            CREATE TABLE Artista (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(250),
                edad INTEGER,
                nacionalidad VARCHAR(250),
                fecha_nacimiento DATE
            )
        """.trimIndent()
        db?.execSQL(scriptSqlCrearArtista)

        // Crear tabla Obra
        val scriptSqlCrearObra = """
            CREATE TABLE Obra (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo VARCHAR(250),
                anio_creacion INTEGER,
                tipo_obra VARCHAR(250),
                precio_estimado REAL,
                disponible BOOLEAN,
                artista_id INTEGER,
                FOREIGN KEY (artista_id) REFERENCES Artista(id) ON DELETE CASCADE
            )
        """.trimIndent()
        db?.execSQL(scriptSqlCrearObra)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS Obra")
            db?.execSQL("DROP TABLE IF EXISTS Artista")
            onCreate(db) // Recrea las tablas
        }
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true) // Habilita las claves foráneas
    }
}