import java.util.Scanner

fun main() {
    val crud = ArtistaObraCRUD()
    val gestionObras = GestionObras(crud)
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\n--- Registro de Artistas ---")
        println("1. Crear Artista")
        println("2. Leer Artistas")
        println("3. Actualizar Artista")
        println("4. Eliminar Artista")
        println("5. Salir")
        print("Seleccione una opción: ")

        val opcion = try {
            scanner.nextInt()
        } catch (e: Exception) {
            scanner.nextLine() // Limpiar el buffer
            println("Entrada inválida. Por favor, ingrese un número.")
            continue
        }

        when (opcion) {
                1 -> {
                    println("\n--- Crear Artista ---")
                    print("Nombre: ")
                    val nombre = scanner.next()
                    print("Edad: ")
                    val edad = validarInt(scanner, "Edad")
                    print("Nacionalidad: ")
                    val nacionalidad = scanner.next()
                    print("Fecha de nacimiento (dd/MM/yyyy): ")
                    val fechaNacimiento = scanner.next()

                    println("¿Cuántas obras desea agregar?")
                    val numObras = validarInt(scanner, "Número de obras")
                    val obras = mutableListOf<Obra>()
                    for (i in 1..numObras) {
                        println("\nObra $i:")
                        obras.add(gestionObras.crearObraDesdeEntrada(scanner))
                    }

                    val artista = Artista(nombre, edad, nacionalidad, obras.size, fechaNacimiento, obras)
                    crud.crearArtista(artista)
                    println("¡Artista creado exitosamente!")
                }

                2 -> {
                    println("\n--- Lista de Artistas ---")
                    val artistas = crud.leerArtistas()
                    if (artistas.isEmpty()) {
                        println("No hay artistas registrados.")
                    } else {
                        println("\n%-20s %-5s %-15s %-15s %-40s".format("Nombre", "Edad", "Nacionalidad", "Fecha Nac.", "Obras"))
                        println("-".repeat(100))
                        artistas.forEachIndexed { index, artista ->
                            println(
                                "%d. %-20s %-5d %-15s %-15s %-40s".format(
                                    index + 1,
                                    artista.nombre,
                                    artista.edad,
                                    artista.nacionalidad,
                                    artista.fechaNacimiento,
                                    artista.obras.joinToString(", ") { it.titulo }
                                )
                            )
                        }

                        print("\nSeleccione un artista para gestionar sus obras (ingrese el número o 0 para volver): ")
                        val seleccion = validarInt(scanner, "Selección")
                        if (seleccion in 1..artistas.size) {
                            gestionObras.gestionar(scanner, artistas[seleccion - 1])
                        } else if (seleccion != 0) {
                            println("Selección inválida.")
                        }
                    }
                }

            3 -> {
                val artistas = crud.leerArtistas()
                if (artistas.isEmpty()) {
                    println("No hay artistas registrados para Editar.")
                } else {
                    println("\nArtistas disponibles:")
                    artistas.forEachIndexed { index, artista ->
                        println("${index + 1}. ${artista.nombre}")
                    }
                }
                println("\n--- Actualizar Artista ---")
                print("Ingrese el nombre del artista a actualizar: ")
                val nombre = scanner.next()
                val artista = crud.leerArtistas().find { it.nombre == nombre }
                if (artista != null) {
                    println("Ingrese los nuevos datos del artista (deje en blanco para no modificar):")

                    // Nombre
                    print("Nombre [${artista.nombre}]: ")
                    val nuevoNombre = scanner.nextLine().takeIf { it.isNotBlank() } ?: artista.nombre

                    // Edad
                    print("Edad [${artista.edad}]: ")
                    val nuevaEdad = scanner.nextLine().takeIf { it.isNotBlank() }?.let { validarIntOpcional(it, "Edad") } ?: artista.edad
                    // Nacionalidad
                    print("Nacionalidad [${artista.nacionalidad}]: ")
                    val nuevaNacionalidad = scanner.nextLine().takeIf { it.isNotBlank() } ?: artista.nacionalidad

                    // Fecha de nacimiento
                    print("Fecha de nacimiento (dd/MM/yyyy) [${artista.fechaNacimiento}]: ")
                    val nuevaFechaNacimiento = scanner.nextLine().takeIf { it.isNotBlank() } ?: artista.fechaNacimiento

                    // Obras
                    println("¿Desea actualizar las obras? (s/n)")
                    val actualizarObras = scanner.nextLine().lowercase() == "s"
                    val nuevasObras = if (actualizarObras) {
                        println("¿Cuántas obras desea agregar?")
                        val numObras = validarInt(scanner, "Número de obras")
                        mutableListOf<Obra>().apply {
                            for (i in 1..numObras) {
                                println("\nObra $i:")
                                add(gestionObras.crearObraDesdeEntrada(scanner))
                            }
                        }
                    } else {
                        artista.obras
                    }

                    // Crear el objeto actualizado
                    val artistaActualizado = Artista(
                        nuevoNombre, nuevaEdad, nuevaNacionalidad, nuevasObras.size, nuevaFechaNacimiento, nuevasObras
                    )

                    // Actualizar en el CRUD
                    crud.actualizarArtista(nombre, artistaActualizado)
                    println("¡Artista actualizado exitosamente!")
                } else {
                    println("Artista no encontrado.")
                }
            }

                4 -> {
                    println("\n--- Eliminar Artista ---")
                    val artistas = crud.leerArtistas()
                    if (artistas.isEmpty()) {
                        println("No hay artistas registrados para eliminar.")
                    } else {
                        println("\nArtistas disponibles:")
                        artistas.forEachIndexed { index, artista ->
                            println("${index + 1}. ${artista.nombre}")
                        }

                        print("\nIngrese el nombre del artista que desea eliminar: ")
                        val nombre = scanner.next()

                        if (artistas.any { it.nombre == nombre }) {
                            crud.eliminarArtista(nombre)
                            println("¡Artista '$nombre' eliminado exitosamente!")
                        } else {
                            println("El artista con nombre '$nombre' no existe.")
                        }
                    }
                }

                5 -> {
                    println("¡Saliendo del programa!")
                    break
                }

                else -> println("Opción no válida. Intente de nuevo.")
            }
        }
    }
