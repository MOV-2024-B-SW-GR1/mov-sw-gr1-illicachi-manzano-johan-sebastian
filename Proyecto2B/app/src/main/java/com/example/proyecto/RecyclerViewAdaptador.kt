package com.example.proyecto

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.CrearTransaccion

class RecyclerViewAdaptador(
    private val contexto: ListTransacciones,
    private val lista: List<Transaccion>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<
        RecyclerViewAdaptador.MyViewHolder
        >() {
    inner class MyViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view){
        val categoriaTextView: TextView
        val montoTextView: TextView
        val categoriaColorTextView: TextView
        val background: TextView
        val editarBoton: Button
        val eliminarBoton: Button
        init{
            categoriaTextView = view.findViewById<TextView>(R.id.tv_categorias)
            montoTextView = view.findViewById<TextView>(R.id.tv_monto_item)
            categoriaColorTextView = view.findViewById<TextView>(R.id.tv_color_subcategoria)
            background = view.findViewById<TextView>(R.id.tv_background)
            editarBoton = view.findViewById<Button>(R.id.btn_editar)
            eliminarBoton = view.findViewById<Button>(R.id.btn_eliminar)
            editarBoton.setOnClickListener {  }
            eliminarBoton.setOnClickListener { eliminarTransaccion(adapterPosition) }

        }


    }
    // Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_view_vista, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
    // Seteamos los datos para la iteracion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val transaccionActual = lista[position]
        var colorBackground = ""
        var colorCategoria= "#000000"
        holder.categoriaTextView.text = transaccionActual.categoria
        holder.montoTextView.text = transaccionActual.monto.toString()

        if (transaccionActual.categoria == "Alimentación")
            colorCategoria = "#761A45FF"
        if (transaccionActual.categoria == "Entretenimiento")
            colorCategoria = "#FF5722FF"
        if (transaccionActual.categoria == "Transporte")
            colorCategoria = "#22A3FFFF"
        if (transaccionActual.categoria == "Servicios Básicos")
            colorCategoria = "#6C22FFFF"

        holder.categoriaColorTextView.setBackgroundColor(Color.parseColor(colorCategoria))


        if (transaccionActual.tipo == "gasto")
            colorBackground = "#CE8383"
        if (transaccionActual.tipo == "ingreso")
            colorBackground = "#838ECEFF"

        holder.background.setBackgroundColor(Color.parseColor(colorBackground))


        holder.editarBoton.setOnClickListener {
            val intent = Intent(contexto, CrearTransaccion::class.java).apply {
                putExtra("id", transaccionActual.id)
                putExtra("monto", transaccionActual.monto)
                putExtra("tipo", transaccionActual.tipo)
                putExtra("categoria", transaccionActual.categoria)
                putExtra("descripcion", transaccionActual.descripcion)
            }
            contexto.startActivity(intent)
        }
    }

    fun eliminarTransaccion(posicion: Int) {
        val transaccionAEliminar = lista[posicion]

        // Eliminar de la base de datos
        contexto.respuesta.eliminarTransaccion(transaccionAEliminar.id)

        // Eliminar de la lista

        // Notificar cambios
        notifyItemRemoved(posicion)
        notifyItemRangeChanged(posicion, lista.size)
    }

    }

