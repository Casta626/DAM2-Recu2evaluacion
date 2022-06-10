

class ServicioBaseDatos {

    /** simula una BD mediante un map*/
    private val bd = mutableMapOf<Int, String>(
        1 to "uno",
        33 to "treinta y tres",
        0 to "cero",
        44 to "cuarenta y cuatro"
    )

    fun obtenerDatoById(id:Int): Dato? {
        val listaContactos : MutableList<String> = mutableListOf()
        val listaTelefonos : MutableList<String> = mutableListOf()

        for (key in bd.keys.sorted()){
            listaContactos.add(key.toString())
            listaTelefonos.add(bd[key].toString())
        }

        //Falta Identificar que la primera letra la identifique tambien como mayuscula.
        val lista = listaContactos.toList()
        println(lista.filter { it.contains(id.toString()) })
        var cont = 0

        return Dato(cont, lista.filter { it.contains(id.toString()) }.toString())
    }

    fun obtenerDatos(): List<Dato> {
        var obtencion_datos = mutableListOf<Dato>()
        println("Listado de Datos:")
        for (key in bd.keys.sorted()) {
            var a = bd[key]
            //obtencion_datos.add( key, a) //Aqui me he bloqueado pero la cosa es pasarle el id y luego el texto, lo que
            // pasa que me estoy rayando porque me pide un int y un tipo dato y aunque haga un intento con un numero y
            // una letra creado a mano no me lo pilla, pero la esencia es pillar el int y el texto para mandarlo
            println(bd[key])
        }
        //return consultaBDordenada

        return obtencion_datos.toList()
    }

    fun eliminaDato(id:Int): Boolean {
        return if (bd.containsKey(id)) {
            println("Se ha eliminado el id $id")
            bd.keys.remove(id)
            true
        }else{
            false
        }
    }

    fun enviarDatos(dato: Dato): Boolean {
        //Para hacerlo mas rapido he pasado el obejto direcatmente
        println("Se ha enviado el dato $dato")
        return dato!=null
    }

}

class Dato(id: Int, texto: String) {
    var datos2 = mutableMapOf<Int,String>(
        Pair(id,texto)
    )

}

class Dato2(dato: MutableMap<Int,String>){

}

class GestionNegocio {

    /**
     * Servicio de acceso a datos
     */
    private val servicioAccesoDatos = ServicioBaseDatos()
    //private val servicioAccesoDatosBD = ServicioAccesoDatosAPI()

    /**
     * Obtener el dato identificado por id
     *
     * @param id El identificador del dato a obtener
     * @return El dato obtenido, null si no lo encuentra
     */
    fun obtenerDatoById(id:Int): Dato? {
        return servicioAccesoDatos.obtenerDatoById(id);
    }

    /**
     * Obtiene todos los datos.
     *
     * @return La lista de todos los datos. Lista vacía si no existe nada.
     */
    fun obtenerDatos(): List<Dato> {
        return servicioAccesoDatos.obtenerDatos();
    }

    /**
     * Elimina dato identificado por id
     *
     * @param id Identificador del datos a eliminar
     * @return true si existia y se elimino. False en caso contrario
     */
    fun eliminaDato(id:Int): Boolean {
        return servicioAccesoDatos.eliminaDato(id)
    }

    /**
     * Enviar datos, inserta si no existe, si existe lo actualiza.
     *
     * @param dato el dato a enviar para persistir
     * @return true si fue una inserción, false si fue una actualización
     */
    fun enviarDatos(dato:Dato) : Boolean {
        return servicioAccesoDatos.enviarDatos(dato)
    }
}

/**
 * Main Prueba del código
 *
 * @param args
 */
fun main(args: Array<String>) {
    val gestionNegocio = GestionNegocio()
    gestionNegocio.enviarDatos(Dato(50, "Cincuenta"))
    gestionNegocio.enviarDatos(Dato(50, "Cincuenta y uno"))
    gestionNegocio.eliminaDato(0)
    println(gestionNegocio.obtenerDatos())
}