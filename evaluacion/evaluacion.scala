import util.control.Breaks._

object Evaluacion {
    def main(args: Array[String]): Unit = {
        println(esPar(7))
        println(hayPares(List(1,3,5,7)))
        println(afortunadoSiete(List(1,3,5,7, 7, 9, 0)))
        println(esPalindromo("Anita lava la tina"))
        println(equilibrio(List(1, 5, 3, 3)))
        println(equilibrio(List(7,3,4)))
    }

    // Verifica si un número es Par
    def esPar(num: Int): Boolean = if(num % 2 == 0) true else false

    // Busca números pares en una lista
    def hayPares(lista: List[Int]): Boolean = {
        // Itera la lista
        for(x <- lista){
            // Revisa si el numero actual es par, si es así, devuelve verdadero y deja de iterar.
            if(esPar(x)){
                return true
                break
            }
        }
        // Si no encuentra un número par devuelve falso
        false
    }

    // Revisa si la suma de la mitad de la lista es igual a la suma de la otra mitad
    def equilibrio(lista: List[Int]): Boolean = {
        val index = lista.length/2

        // Obtiene dos listas a partir de la original
        val mitad1 = lista.slice(0, index)
        val mitad2 = lista.slice(index, lista.length)

        // Revisa si la suma es igual
        if(mitad1.sum == mitad2.sum) true else false
    }

    def afortunadoSiete(lista: List[Int]): Int = {
        var suma = 0

        // Itera la lista buscando sietes, si encuentra uno suma 14, si no suma el numero original.
        for(x <- lista) if(x == 7) suma += 14 else suma += x

        // Devuelve el resultado
        suma
    }



    def esPalindromo(cadena: String): Boolean = {
        // Elimina los espacios de la cadena original y la converte a minúsculas
        val cadenaLimpia = cadena.replaceAll(" ", "").toLowerCase()

        // Revisa si la cadena es igual inversamente
        if(cadenaLimpia == cadenaLimpia.reverse) true else false
    }
}