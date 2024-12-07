import java.util.*

fun main (args: Array<String>) {
    //Inmutables (No se re asigna "=") constantes
    val inmutable: String = "Johan"
    //inmutable = "Sebastian" //Error

    //Mutables (se pueden reasignar) variables
    var mutable: String = "Johan"
    mutable = "Sebastian"

    //Duck typing
    val ejemploVariable = "Johan Sebastian"
    ejemploVariable.trim()

    val edadEjemplo: Int = 12
    val fechaNacimiento: Date = Date()

    val estadoCivilWhen = "C"
    when(estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"
    imprimirNombre("Johan")

    calcularSueldo(10.00)//solo parametros requeridos
    //con parametros opcionales
    calcularSueldo(10.00, 15.00, 20.00)

    //Named parameters
    //calcularsueldo (sueldo, tasa, bonoEspecial)
    //Colocamos el parametro bonoEspecial en 2 lugar
    calcularSueldo(10.00, bonoEspecial = 20.00)
    //Colocamos los parametros en diferentes posiciones
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 15.00)

    //CLASES USO:
    val sumaA = suma(1,1)
    val sumaB = suma(null,1)
    val sumaC = suma(1,null)
    val sumaD = suma(null,null)
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(suma.pi)
    println(suma.elevarAlCuadrado(2))
    println(suma.historialSumas)

    //Arreglos
    //Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico);
    //Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOR EACH = > Unit
    //Iterar un arreglo
    val respueustaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}");
        }
    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach { println("Valor Actual (it): ${it}")}

    //MAP -> MUTA(Modifica cambia) el arreglo
    // 1) Enviamos el nuevo valor de la interacion
    // 2) Nos devuelve un NUEVO ARREGLO con valores
    // de las iteraciones
    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15}
    println(respuestaMapDos)

    //Filter -> Filtrar el ARREGLO
    //1) Devolver una expresion (TRUE o FALSE)
    //2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual:Int ->
            //Expresion o CONDICION
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter { it > 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    //OR -> ANY (Alguno Cumple?)
    //And -> ALL (Todos Cumplen?)
    val respuestaAny:Boolean = arregloDinamico
        .any{ valorActual:Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny)

    val respuestaAll:Boolean = arregloDinamico
        .all { valorActual:Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll)
}

fun imprimirNombre(nombre: String):Unit {
    fun otraFuncionAdentro(){
        println("Otra funcion adentro")
    }
    println("Nombre: $nombre")//con llaves
    println("Nombre: ${nombre}")//sin llaves
    println("Nombre: ${nombre + nombre}")//Para concatenar
    //usamos llaves
    println("Nombre: ${nombre.toString()}")//Para usar una funcion
    //usamos llaves
    println("Nombre: $nombre.toString()")//Incorrecto
    //No se puede usar sin llaves
    otraFuncionAdentro()
}

fun calcularSueldo(
    sueldo:Double, //requerido
    tasa: Double = 12.00, //Opcional (Defecto)
    bonoEspecial: Double? = null//Opcional (nullable)
):Double {
    if(bonoEspecial == null) {
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(numeroUno: Int, numeroDos: Int) {
        this.numeroUno = numeroUno
        this.numeroDos = numeroDos
        println("Inicializado")
    }
}

abstract class NumerosKotlin(
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    parametroNoUsadoNoPropiedadDeLaClase: Int? = null
){
    init {
        this.numeroUno
        this.numeroDos
        println("Inicializado")
    }
}

class suma(
    unoParametro: Int,
    dosParametro: Int,
):NumerosKotlin(
    unoParametro,
    dosParametro
) {

    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicite: String = "Publico implicito"

    init {
        this.numeroUno
        this.numeroDos
        numeroUno
        numeroDos
        this.soyPublicoImplicite
        soyPublicoExplicito
    }

    constructor( //Constructor Secundario
        uno: Int?, //Entero nullable
        dos: Int,
    ):this(
        if (uno == null) 0 else uno,
        dos
    ){
        //Bloque de codigo de constructor secundario
    }

    constructor( //Constructor Secundario
        uno: Int,
        dos: Int?, //Entero nullable
    ):this(
        uno,
        if (dos == null) 0 else dos
    )

    constructor( //Constructor Secundario
        uno: Int?, //Entero nullable
        dos: Int?, //Entero nullable
    ):this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

    fun sumar ():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{ //Comparte entre todas las instancias, similar a Static
        //funciones, variables
        //NombreClase.metodo, NombreClase.funcion
        //Suma.pi
        val pi = 3.14
        //Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{return num * num }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){//Suma.agregarHistorial
            historialSumas.add(valorTotalSuma)
        }
    }
}


