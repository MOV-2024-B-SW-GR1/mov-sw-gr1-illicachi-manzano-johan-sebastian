import java.util.*

class GestionObras(private val crud: ArtistaObraCRUD) {

    fun gestionar(scanner: Scanner, artista: Artista) {
        while (true) {
            println("\n--- Gestión de Obras para '${artista.nombre}' ---")
            println("1. Crear Obra")
            println("2. Leer Obras")
            println("3. Editar Obra")
            println("4. Eliminar Obra")
            println("5. Volver")
            print("Seleccione una opción: ")

            when (val opcion = validarInt(scanner, "Opción")) {
                1 -> crearObra(scanner, artista)
                2 -> leerObras(artista)
                3 -> editarObra(scanner, artista)
                4 -> eliminarObra(scanner, artista)
                5 -> break
                else -> println("Opción no válida. Intente de nuevo.")
            }
        }
    }

    private fun crearObra(scanner: Scanner, artista: Artista) {
        val nuevaObra = crearObraDesdeEntrada(scanner)
        artista.obras.add(nuevaObra)
        crud.actualizarArtista(artista.nombre, artista)
        println("¡Obra creada exitosamente!")
    }

    private fun leerObras(artista: Artista) {
        if (artista.obras.isEmpty()) {
            println("No hay obras registradas para este artista.")
        } else {
            println("\nObras de '${artista.nombre}':")
            println("%-3s %-20s %-15s %-15s %-10s %-15s".format("No.", "Título", "Año Creación", "Tipo de Obra", "Precio", "Disponible"))
            println("-".repeat(80))
            artista.obras.forEachIndexed { index, obra ->
                println(
                    "%-3d %-20s %-15d %-15s %-10.2f %-15s".format(
                        index + 1,
                        obra.titulo,
                        obra.anioCreacion,
                        obra.tipoObra,
                        obra.precioEstimado,
                        if (obra.disponible) "Sí" else "No"
                    )
                )
            }
        }
    }

    private fun editarObra(scanner: Scanner, artista: Artista) {
        println("\nIngrese el título de la obra que desea editar:")
        val titulo = scanner.next()
        val obra = artista.obras.find { it.titulo == titulo }
        if (obra != null) {
            println("\nIngrese los nuevos datos de la obra:")
            artista.obras.remove(obra)
            val obraActualizada = crearObraDesdeEntrada(scanner)
            artista.obras.add(obraActualizada)
            crud.actualizarArtista(artista.nombre, artista)
            println("¡Obra actualizada exitosamente!")
        } else {
            println("Obra no encontrada.")
        }
    }

    private fun eliminarObra(scanner: Scanner, artista: Artista) {
        if (artista.obras.isEmpty()) {
            println("No hay obras registradas para este artista.")
        } else {
            println("\nObras de '${artista.nombre}':")
            println("%-3s %-20s %-15s".format("No.", "Título", "Año Creación"))
            println("-".repeat(50))
            artista.obras.forEachIndexed { index, obra ->
                println(
                    "%-3d %-20s %-15d".format(
                        index + 1,
                        obra.titulo,
                        obra.anioCreacion
                    )
                )
            }

            print("\nIngrese el título de la obra que desea eliminar: ")
            val titulo = scanner.next()
            val obraEliminada = artista.obras.removeIf { it.titulo == titulo }
            if (obraEliminada) {
                crud.actualizarArtista(artista.nombre, artista)
                println("¡Obra eliminada exitosamente!")
            } else {
                println("Obra no encontrada.")
            }
        }
    }

    fun crearObraDesdeEntrada(scanner: Scanner): Obra {
        print("Título: ")
        val titulo = scanner.next()
        print("Año de creación: ")
        val anioCreacion = validarInt(scanner, "Año de creación")
        print("Tipo de obra: ")
        val tipoObra = scanner.next()
        print("Precio estimado: ")
        val precioEstimado = validarDouble(scanner, "Precio estimado")
        print("¿Disponible para exhibición? (true/false): ")
        val disponible = validarBoolean(scanner, "Disponibilidad")
        return Obra(titulo, anioCreacion, tipoObra, precioEstimado, disponible)
    }
}
