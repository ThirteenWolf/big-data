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

    def esPar(num: Int): Boolean = if(num % 2 == 0) true else false

    def hayPares(lista: List[Int]): Boolean = {
        for(x <- lista){
            if(esPar(x)){
                return true
                break
            }
        }
        false
    }

    def equilibrio(lista: List[Int]): Boolean = {
        val index = lista.length/2

        val mitad1 = lista.slice(0, index)
        val mitad2 = lista.slice(index, lista.length)

        if(mitad1.sum == mitad2.sum) true else false
    }

    def afortunadoSiete(lista: List[Int]): Int = {
        var suma = 0

        for(x <- lista) if(x == 7) suma += 14 else suma += x

        suma
    }



    def esPalindromo(cadena: String): Boolean = {
        val cadenaLimpia = cadena.replaceAll(" ", "").toLowerCase()
        if(cadenaLimpia == cadenaLimpia.reverse) true else false
    }
}