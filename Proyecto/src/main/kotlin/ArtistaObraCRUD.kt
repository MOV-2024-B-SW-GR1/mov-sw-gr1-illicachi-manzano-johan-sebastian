import java.io.File

class ArtistaObraCRUD {
    private val archivoArtistas = "artistas.txt"

    init {
        val file = File(archivoArtistas)
        if (!file.exists()) file.createNewFile()
    }

    fun crearArtista(artista: Artista) {
        val file = File(archivoArtistas)
        file.appendText("${artista.nombre}|${artista.edad}|${artista.nacionalidad}|${artista.numObras}|${artista.fechaNacimiento}|${serializeObras(artista.obras)}\n")
    }

    fun leerArtistas(): List<Artista> {
        val file = File(archivoArtistas)
        return file.readLines().mapNotNull { deserializeArtista(it) }
    }

    fun actualizarArtista(nombre: String, artistaActualizado: Artista) {
        val artistas = leerArtistas().toMutableList()
        val index = artistas.indexOfFirst { it.nombre == nombre }
        if (index != -1) {
            artistas[index] = artistaActualizado
            guardarArtistas(artistas)
        }
    }

    fun eliminarArtista(nombre: String) {
        val artistas = leerArtistas().filter { it.nombre != nombre }
        guardarArtistas(artistas)
    }

    private fun guardarArtistas(artistas: List<Artista>) {
        val file = File(archivoArtistas)
        file.writeText("")
        artistas.forEach { crearArtista(it) }
    }

    private fun serializeObras(obras: List<Obra>): String {
        return obras.joinToString(";") { "${it.titulo},${it.anioCreacion},${it.tipoObra},${it.precioEstimado},${it.disponible}" }
    }

    private fun deserializeArtista(line: String): Artista? {
        val parts = line.split("|")
        if (parts.size < 5) return null
        val obras = parts.getOrNull(5)?.split(";")?.mapNotNull {
            val obraParts = it.split(",")
            if (obraParts.size == 5) {
                Obra(
                    titulo = obraParts[0],
                    anioCreacion = obraParts[1].toInt(),
                    tipoObra = obraParts[2],
                    precioEstimado = obraParts[3].toDouble(),
                    disponible = obraParts[4].toBoolean()
                )
            } else null
        } ?: listOf()
        return Artista(parts[0], parts[1].toInt(), parts[2], parts[3].toInt(), parts[4], obras.toMutableList())
    }
}
