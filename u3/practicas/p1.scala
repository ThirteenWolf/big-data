// imports de librerías que se van a usar
import org.apache.spark.sql.SparkSession

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()

import org.apache.spark.ml.feature.{VectorAssembler}
import org.apache.spark.ml.clustering.KMeans

// Leemos los datos
val df = spark.read.option("header", "true").option("inferSchema","true").csv("Wholesale customers data.csv")

// Juntamos las columnas que vamos a usar para el k-means en una columna llamada "features"
val assembler = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper", "Delicassen")).setOutputCol("features")

val dataset = assembler.transform(df)

// Creamos y entrenamos el modelo k-means con 2 grupos
val kmeans = new KMeans().setK(2).setSeed(1L)
val model = kmeans.fit(dataset)

// Evaluate clustering by calculate Within Set Sum of Squared Errors.
val WSSE = model.computeCost(dataset)
println(s"Within set sum of Squared Errors = $WSSE")

// Imprimimos los centros de cada cluster
println("Cluster Centers: ")
model.clusterCenters.foreach(println)