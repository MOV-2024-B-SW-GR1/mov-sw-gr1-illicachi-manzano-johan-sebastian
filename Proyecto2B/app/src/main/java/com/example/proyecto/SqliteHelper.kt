package com.example.proyecto

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaTransaccion =
            """
                CREATE TABLE TRANSACCION(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fecha_actual TEXT,
                    monto DOUBLE,
                    tipo TEXT CHECK(tipo IN ('gasto', 'ingreso')),
                    categoria TEXT,
                    descripcion TEXT
                    
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaTransaccion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearTransaccion(fecha_actual: String, monto: Double, tipo: String, categoria: String, descripcion: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("fecha_actual", fecha_actual)
        valoresGuardar.put("monto", monto)
        valoresGuardar.put("tipo", tipo)
        valoresGuardar.put("categoria", categoria)
        valoresGuardar.put("descripcion", descripcion)
        val resultadoGuardar = baseDatosEscritura.insert("TRANSACCION", null, valoresGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun eliminarTransaccion(id: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminar = baseDatosEscritura.delete("TRANSACCION", "id=?", parametrosConsultaDelete)
        baseDatosEscritura.close()
        return resultadoEliminar > 0
    }

    fun actualizarTransaccion(id: Int, fecha_actual: String,  monto: Double, tipo: String, categoria: String, descripcion: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("fecha_actual", fecha_actual)
        valoresAActualizar.put("monto", monto)
        valoresAActualizar.put("tipo", tipo)
        valoresAActualizar.put("categoria", categoria)
        valoresAActualizar.put("descripcion", descripcion)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizar = baseDatosEscritura.update("TRANSACCION", valoresAActualizar, "id=?", parametrosConsultaActualizar)
        baseDatosEscritura.close()
        return resultadoActualizar > 0
    }

    fun consultarTransaccionPorId(id: Int): Transaccion? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM TRANSACCION WHERE id = ?"
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        val existeAlMenosUno = resultadoConsultaLectura.moveToFirst()
        if (existeAlMenosUno) {
            val transaccion = Transaccion(
                resultadoConsultaLectura.getInt(0), // id
                resultadoConsultaLectura.getString(1), // fecha_actual
                resultadoConsultaLectura.getDouble(2), // monto
                resultadoConsultaLectura.getString(3), // tipo
                resultadoConsultaLectura.getString(4), // categoria
                resultadoConsultaLectura.getString(5) // descripcion
            )
            resultadoConsultaLectura.close()
            return transaccion
        }
        resultadoConsultaLectura.close()
        return null
    }

    fun listarTransacciones(): List<Transaccion> {
        val listaTransacciones = ArrayList<Transaccion>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM TRANSACCION"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        while (resultadoConsultaLectura.moveToNext()) {
            val transaccion = Transaccion(
                resultadoConsultaLectura.getInt(0), // id
                resultadoConsultaLectura.getString(1), // fecha_actual
                resultadoConsultaLectura.getDouble(2), // monto
                resultadoConsultaLectura.getString(3), // tipo
                resultadoConsultaLectura.getString(4), // categoria
                resultadoConsultaLectura.getString(5) // descripcion
            )
            listaTransacciones.add(transaccion)
        }
        resultadoConsultaLectura.close()
        return listaTransacciones
    }


}


