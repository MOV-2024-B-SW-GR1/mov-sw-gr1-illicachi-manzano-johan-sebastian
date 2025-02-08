package com.example.debermoviles

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context?) : SQLiteOpenHelper(
    context,
    "ArtistasDB", // Nombre de la base de datos
    null,
    2 // Nueva versión de la base de datos
) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Crear tabla Artista con latitud y altitud
        val scriptSqlCrearArtista = """
            CREATE TABLE Artista (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(250),
                edad INTEGER,
                nacionalidad VARCHAR(250),
                fecha_nacimiento DATE,
                latitud REAL,
                altitud REAL
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
        if (oldVersion < 2) {
            // Agregar las columnas latitud y altitud si la versión anterior es menor a 2
            db?.execSQL("ALTER TABLE Artista ADD COLUMN latitud REAL DEFAULT 0.0")
            db?.execSQL("ALTER TABLE Artista ADD COLUMN altitud REAL DEFAULT 0.0")
        }
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true) // Habilita las claves foráneas
    }
}
