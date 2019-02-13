// 1. Crea una lista llamad "lista" con los elementos "rojo", "blanco", "negro"
val x = List("rojo", "blanco", "negro")
println(x)
// 2. Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla"
val y = x ++ List("verde", "amarillo", "azul", "naranja", "perla") 

println(y)
// 3. Traer los elementos de "lista" "verde", "amarillo", "azul"
println(y slice(3, 6))
// 4. Crea un arreglo de numero en rango del 1-1000 en pasos de 5 en 5
val z = Array.range(0, 1000, 5)
for(i <- z) print(i+ ", ")
println()
// 5. Cuales son los elementos unicos de la lista Lista(1,3,3,4,6,7,3,7) utilice conversion a conjuntos
val lista = List(1,3,3,4,6,7,3,7)
val set = lista.toSet
for(x <- set) println(x)
// 6. Crea una mapa mutable llamado nombres que contenga los siguiente
//     "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"
val map = collection.mutable.Map(("Jose", 20), ("Luis", 24), ("Ana", 23), ("Susana", "27"))

// 6 a . Imprime todas la llaves del mapa
println(map.keys)
// 7 b . Agrega el siguiente valor al mapa("Miguel", 23)
map += ("Miguel" -> 23)
println(map)