object Practica1 {
    def main(args: Array[String]): Unit = {
        val metros = 0.3048
        var pies = 3

        println(s"${pies}ft = " + metros * pies + "m")

        pies = 10

        println(s"${pies}ft = " + metros * pies + "m")

        pies = 15

        println(s"${pies}ft = " + metros * pies + "m")

        val pesos = 18.75
        var dolares = 3

        println(s"${dolares} dolares = " + dolares * pesos + "pesos")

        dolares = 10

        println(s"${dolares} dolares = " + dolares * pesos + "pesos")

        dolares = 15

        println(s"${dolares} dolares = " + dolares * pesos + "pesos")

        val kilometros = 9.32057
        var millas = 3

        println(s"${millas} millas = " + millas * kilometros + "km")

        millas = 10

        println(s"${millas} millas = " + millas * kilometros + "km")

        millas = 15

        println(s"${millas} millas = " + millas * kilometros + "km")
    }
}