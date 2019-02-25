object Practica2 {
    def main(args: Array[String]): Unit = {
        // Assessment 1/Practica 1
        //1. Desarrollar un algoritmo en scala que calcule el radio de un circulo
        val diametro = 20

        println(diametro/2)
        //2. Desarrollar un algoritmo en scala que me diga si un numero es primo
        val numero = 5
        if(numero%2 == 0) println(s"${numero} es par")
        else println(s"${numero} es impar")
        //3. Dada la variable bird = "tweet", utiliza interpolacion de string para
        //   imprimir "Estoy ecribiendo un tweet"
        val bird: String = "tweet"

        println(s"Estoy escribiendo un ${bird}")
        //4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slilce para extraer la
        //   secuencia "Luke"
        val mensaje = "Hola Luke yo soy tu padre!"
        val word = mensaje.slice(5, 5 + 4)
        println(word)
        //5. Cual es la diferencia en value y una variable en scala?
        //Las variables mutables se describen con la palabra clave "var". 
        //A diferencia de val, "var" se puede reasignar a valores diferentes 
        //o apuntar a objetos diferentes. Pero tienen que ser inicializados 
        //en el momento de la declaración.
        
        //6. Dada la tupla ((2,4,5),(1,2,3),(3.1416,23))) regresa el numero 3.1416 
        val tupla = ((2,4,5),(1,2,3),(3.1416,23))
        println(tupla._3._1)
    }
}