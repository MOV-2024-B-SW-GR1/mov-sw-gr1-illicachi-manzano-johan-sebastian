import java.util.Scanner

fun validarInt(scanner: Scanner, campo: String): Int {
    while (true) {
        try {
            return scanner.nextInt()
        } catch (e: Exception) {
            scanner.nextLine() // Limpiar el buffer
            println("Entrada inválida. Por favor, ingrese un número entero.")
        }
    }
}


fun validarIntOpcional(input: String, campo: String): Int {
    return try {
        input.toInt()
    } catch (e: NumberFormatException) {
        println("$campo debe ser un número válido. Inténtelo de nuevo.")
        -1 // Puedes retornar un valor por defecto o manejarlo como prefieras
    }
}



fun validarDouble(scanner: Scanner, campo: String): Double {
    while (true) {
        try {
            return scanner.nextDouble()
        } catch (e: Exception) {
            scanner.nextLine() // Limpiar el buffer
            println("Entrada inválida. Por favor, ingrese un número decimal.")
        }
    }
}

fun validarBoolean(scanner: Scanner, campo: String): Boolean {
    while (true) {
        try {
            return scanner.nextBoolean()
        } catch (e: Exception) {
            scanner.nextLine() // Limpiar el buffer
            println("Entrada inválida. Por favor, ingrese 'true' o 'false'.")
        }
    }
}
