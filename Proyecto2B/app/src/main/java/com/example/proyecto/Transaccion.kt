package com.example.proyecto

import android.os.Parcel
import android.os.Parcelable

data class Transaccion(
    var id: Int,
    var fecha_actual: String,
    var monto: Double,
    var tipo: String,
    var categoria: String,
    var descripcion: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()
    )

    override fun toString(): String {
        return "$fecha_actual | $monto| $tipo | $categoria | $descripcion  "
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(fecha_actual)
        parcel.writeDouble(monto)
        parcel.writeString(tipo)
        parcel.writeString(categoria)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaccion> {
        override fun createFromParcel(parcel: Parcel): Transaccion {
            return Transaccion(parcel)
        }

        override fun newArray(size: Int): Array<Transaccion?> {
            return arrayOfNulls(size)
        }
    }
}
