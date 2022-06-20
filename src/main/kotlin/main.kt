
interface InjeccionDependencias {

    fun obtenerDatoById(id:Int): Dato?

    fun obtenerDatos(): List<Dato>

    fun eliminaDato(id:Int): Boolean

    fun enviarDatos(dato: Dato): Boolean

}
    val rojo = "\u001B[31m"
    val verde = "\u001B[32m"
    val amarillo = "\u001B[33m"
    val azul = "\u001B[34m"
    val purpura = "\u001B[35m"
    val cian = "\u001B[36m"
    val blanco = "\u001B[37m"


class ServicioBaseDatos : InjeccionDependencias  {

    private val c = DB()
    private val bd = c.selectAll()

    override fun obtenerDatoById(id:Int): Dato? {
        val listaContactos : MutableList<String> = mutableListOf()

        for (key in bd.keys.sorted()){
            listaContactos.add(key.toString())
            listaContactos.add(bd[key].toString())
        }

        val lista = listaContactos.toList()
        return if (lista.contains(id.toString())){
            println(verde+"El id $id existe, aquí tiene su información"+blanco)

            Dato(id, listaContactos[lista.indexOf(id.toString())])
        } else{
            println(rojo+"No existe el id $id"+blanco)
            null
        }


    }

    override fun obtenerDatos(): List<Dato> {
        val obtencion_datos = mutableListOf<Dato>()
        println(purpura+"Listado de Datos:"+blanco)
        for (key in bd.keys.sorted()) {
            obtencion_datos.add(Dato(key, bd[key]!!))
            println(azul+"${key} $blanco- $cian${bd[key]}"+blanco)

        }

        return obtencion_datos.toList()
    }

    override fun eliminaDato(id:Int): Boolean {
        return if (bd.containsKey(id)) {
            println(verde+"Se ha eliminado el id $id correctamente"+blanco)
            bd.remove(id)
            true
        }else{
            println(rojo+"No existe el id $id"+blanco)
            false
        }
    }

    override fun enviarDatos(dato: Dato): Boolean {

        if (bd.containsKey(dato.id)) {
            println(rojo+"El id ${dato.id} ya existe"+blanco)
            return false
        }else{
            bd[dato.id] = dato.texto
            println(verde+"Se ha añadido el id ${dato.id}"+blanco)
            return true
        }

    }

}

class Dato(id: Int, texto: String) {
    var id = id
    var texto = texto
}

class GestionNegocio (var fusion: InjeccionDependencias){

    /**
     * Servicio de acceso a datos
     */
    //private val servicioAccesoDatos = ServicioBaseDatos()
    //private val servicioAccesoDatosBD = ServicioAccesoDatosAPI()

    /**
     * Obtener el dato identificado por id
     *
     * @param id El identificador del dato a obtener
     * @return El dato obtenido, null si no lo encuentra
     */
    fun obtenerDatoById(id:Int): Dato? {
        return fusion.obtenerDatoById(id)
    }

    /**
     * Obtiene todos los datos.
     *
     * @return La lista de todos los datos. Lista vacía si no existe nada.
     */
    fun obtenerDatos(): List<Dato> {
        return fusion.obtenerDatos()
    }

    /**
     * Elimina dato identificado por id
     *
     * @param id Identificador del datos a eliminar
     * @return true si existia y se elimino. False en caso contrario
     */
    fun eliminaDato(id:Int): Boolean {
        return fusion.eliminaDato(id)
    }

    /**
     * Enviar datos, inserta si no existe, si existe lo actualiza.
     *
     * @param dato el dato a enviar para persistir
     * @return true si fue una inserción, false si fue una actualización
     */
    fun enviarDatos(dato:Dato) : Boolean {
        return fusion.enviarDatos(dato)
    }
}
    class DB(){
        /** simula una BD mediante un map*/
        private val bd = mutableMapOf<Int, String>(
            1 to "uno",
            33 to "treinta y tres",
            0 to "cero",
            44 to "cuarenta y cuatro"
        )
        fun selectAll(): MutableMap<Int, String>{
            return bd
        }
    }
    class ServicioAccesoDatosAPI : InjeccionDependencias {

        private val a = DB()
        private val bd = a.selectAll()

        private val servicioAPI_String : MutableList<String> = mutableListOf()
        private val servicioAPI_Dato : MutableList<Dato> = mutableListOf()

        override fun obtenerDatoById(id: Int): Dato? {
            for (key in bd.keys.sorted()){
                servicioAPI_String.add(key.toString())
                servicioAPI_String.add(bd[key].toString())
            }

            val lista = servicioAPI_String.toList()
            return if (lista.contains(id.toString())){
                println(cian+"El id $id existe, aquí tiene su información"+blanco)

                return Dato(id, servicioAPI_String[lista.indexOf(id.toString())])
            }
            else{
                println(amarillo+"No existe el id $id"+blanco)
                return null
            }
        }

        override fun obtenerDatos(): List<Dato> {

            println(azul + "Listado de Datos:" + blanco)
            for (key in bd.keys.sorted()) {
                servicioAPI_Dato.add(Dato(key, bd[key]!!))
                println(rojo + "${key} $blanco- $verde${bd[key]}" + blanco)
            }
            return servicioAPI_Dato.toList()
        }

        override fun eliminaDato(id: Int): Boolean {
            return if (bd.containsKey(id)) {
                println(cian+"Se ha eliminado el id $id correctamente"+blanco)
                bd.remove(id)
                true
            }else{
                println(amarillo+"No existe el id $id"+blanco)
                false
            }
        }

        override fun enviarDatos(dato: Dato): Boolean {
            if (bd.containsKey(dato.id)) {
                println(amarillo+"El id ${dato.id} ya existe"+blanco)
                return false
            }else{
                bd[dato.id] = dato.texto
                println(cian+"Se ha añadido el id ${dato.id}"+blanco)
                return true
            }
        }
    }
/**
 * Main Prueba del código
 *
 * @param args
 */
fun main() {

    val gestionNegocio = GestionNegocio(ServicioBaseDatos())
    println(verde+"Servicio normal")
    println(gestionNegocio.obtenerDatoById(1))
    println(gestionNegocio.obtenerDatoById(2))
    println(gestionNegocio.enviarDatos(Dato(50, "Cincuenta")))
    println(gestionNegocio.enviarDatos(Dato(50, "Cincuenta y uno")))
    println(gestionNegocio.eliminaDato(0))
    println(gestionNegocio.obtenerDatos())
    println("\n\n\n")
    println("Servicio API optimizado")
    gestionNegocio.fusion = ServicioAccesoDatosAPI()
    println(gestionNegocio.obtenerDatoById(1))
    println(gestionNegocio.obtenerDatoById(2))
    println(gestionNegocio.enviarDatos(Dato(50, "Cincuenta")))
    println(gestionNegocio.enviarDatos(Dato(50, "Cincuenta y uno")))
    println(gestionNegocio.eliminaDato(0))
    println(gestionNegocio.obtenerDatos())
}