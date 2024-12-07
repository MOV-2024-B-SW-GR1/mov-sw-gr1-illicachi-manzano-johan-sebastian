data class Artista(
    var nombre: String,
    var edad: Int,
    var nacionalidad: String,
    var numObras: Int,
    var fechaNacimiento: String,
    var obras: MutableList<Obra> = mutableListOf()
)
