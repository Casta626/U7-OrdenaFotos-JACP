import java.io.File
import java.io.InputStream
import java.nio.file.Files.write
import java.nio.file.StandardOpenOption

/*
Las cámaras de los teléfonos inteligentes usan el formato IMG_YYYYMMDD_HHMMSS.jpg, donde YYYY es el año, MM es el mes y
DD es el día del mes. Por ejemplo: IMG_20171203_213455.jpg corresponde a una foto tomada el 3 de diciembre de 2017 a las
21:34:55.

Los archivos de la cámara Reflex se almacenan en el formato PDDMMYY_HHMMSS.jpg, donde DD es el día del mes, MM es el mes
y YY son los dos últimos dígitos de un año (suponiendo que es un año del siglo XXI). Por ejemplo: P031217_213455.jpg
corresponde a una foto tomada el 3 de diciembre de 2017, a las 21:34:55.
 */
val reset = "\u001B[0m"
val rojo = "\u001B[31m"
val azul = "\u001B[34m"
val cian = "\u001B[36m"
val verde = "\u001B[32m"
val amarillo = "\u001B[33m"
val purpura = "\u001B[35m"

fun main() {
    var imagenes = mutableListOf<String>()
    val inputStream2: InputStream = File("C:\\Users\\ercas\\Downloads\\U7-OrdenaFotos-JACP\\src\\main\\resources\\test-1.in").inputStream()
    inputStream2.bufferedReader().useLines { lines -> lines.forEach { imagenes.add(it) } }

    fun compararFotos(fotos: MutableList<String> = mutableListOf<String>(), lugar : String):MutableList<String>{
        var fotos2 = mutableListOf<Long>()
        for (i in fotos){
            if (i[0]=='I' && i[1]=='M' && i[2]=='G' && i[19] =='.' && i[20] =='j' && i[21] =='p' && i[22] =='g'){
                var anio = ""
                var mes = ""
                var dia = ""
                var hora = ""
                var min = ""
                var seg = ""
                var size = i.length
                for(j in 4 until size){
                    when (j){
                        //al final podria haber pillado el rango entre los _ pero ya me di cuenta tarde
                        in 4..7 -> anio+=i[j]
                        8,9 -> mes+=i[j]
                        10,11 -> dia+=i[j]
                        13,14 -> hora+=i[j]
                        15,16 -> min+=i[j]
                        17,18 -> seg+=i[j]
                    }
                    //println(i[j])
                }
                var num = (anio+mes+dia+hora+min+seg).toLong()
                fotos2.add(num)
            }else if (i[0]=='P' && i[14] =='.' && i[15] =='j' && i[16] =='p' && i[17] =='g'){
                var anio = ""
                var mes = ""
                var dia = ""
                var tiempo = ""
                var size2 = i.length
                for(k in 1 until size2){
                    when (k){
                        //al final podria haber pillado el rango entre los _ pero ya me di cuenta tarde
                        1,2 -> dia+=i[k]
                        3,4 -> mes+=i[k]
                        5,6 -> anio+=i[k]
                        in 8..13 -> tiempo+=i[k]
                    }
                    //println(i[j])
                }
                var num2 = ("20$anio$mes$dia$tiempo").toLong()
                fotos2.add(num2)
            }else{

            }
        }
        var cont = 0
        var salidaFotos = mutableListOf<String>()
        for (l in fotos2.sortedDescending()){
            //Para arreglarlo podría ponerlo a string los numero ordenados con una letra para que identificase de que tipo
            //son y luego reordenar su estructura pero esta vez con el orden descendiente y ya estaria la imagen correcta.
            salidaFotos.add("mv "+fotos[cont]+" $lugar"+"_00"+cont)
            cont++
        }
        return salidaFotos
    }
    println("$verde¿De qué lugar son las fotos?")
    var sitio = readLine().toString()

    compararFotos(imagenes,sitio)
    val outString = compararFotos(imagenes,sitio)
    val archivo = File("C:\\Users\\ercas\\Downloads\\U7-OrdenaFotos-JACP\\src\\main\\resources\\test-1.ans")
    archivo.writeText("")

    for (y in outString){
        var lin = "\n"
        write(archivo.toPath(), y.toByteArray(), StandardOpenOption.APPEND)
        write(archivo.toPath(), lin.toByteArray(), StandardOpenOption.APPEND)

    }

}