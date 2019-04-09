import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}

val schemaStruct = StructType(
    StructField("sepalLength", DoubleType) ::
    StructField("sepalWidth", DoubleType) ::
    StructField("petalLength", DoubleType) ::
    StructField("petalWidth", DoubleType) ::
    StructField("label", StringType) :: Nil
)

// Se cargan los datos del dataset en un dataframe, usando la estructura definida
// previamente.
val df = spark.read.option("header", "false").schema(schemaStruct)csv("Iris.csv")


// Para poder procesar los datos categoricos se les sustituyen esos datos por numeros indexandolos 
val labelIndexer = new StringIndexer().
    setInputCol("label").
    setOutputCol("labelIndex").
    fit(df)


// Como el modelo nos pide que entreguemos los features en un vector usamos
// el VectorAssembler 
val assembler = new VectorAssembler().
    setInputCols(Array("sepalLength", "sepalWidth", "petalLength", "petalWidth")).
    setOutputCol("features")


// Dividimos los datos, 70% (105 datos) para entrenar el modelo y 30% (45 datos) para evaluarlo
val Array(trainingData, testData) = df.randomSplit(Array(0.7, 0.3))

// b.- Configuramos la arquitectura de la red neuronal, usando 4 capas, en la capa de entrada 
// usamos 4 neuronas por el numero de features de dataset, en la segunda y tercera usamos 4
// neuronas y al final 3 neuronas por cada label distinto del dataset.
val layers = Array[Int](4, 4, 3, 3)


// d.- Es una red neuronal, en este caso de 4 capas, una de entrada, 2 intermedias y una de
// salida. Funciona bien para hacer predicciones basandose en varios "features" a diferencia
// de otros tipos de redes neuronales
val mp = new MultilayerPerceptronClassifier().
    setLayers(layers).
    setLabelCol("labelIndex").
    setFeaturesCol("features").
    setPredictionCol("prediction").
    setSeed(1234L).
    setMaxIter(100)

val stages = Array(labelIndexer, assembler, mp)

// Creamos un pipeline que va a aplicar el mismo procesamiento a los datos que le demos
val pipeline = new Pipeline().setStages(stages)

// Entrenamos el modelo con los datos de entrenamiento
val model = pipeline.fit(trainingData)

// Aplicamos el modelo a los datos de prueba
val result = model.transform(testData)

// Seleccionamos la prediccion y los datos originales para evaluar el rendimiento del modelo
val predictionAndLabels = result.select("prediction", "labelIndex")

val evaluator = new MulticlassClassificationEvaluator().
    setLabelCol("labelIndex").
    setPredictionCol("prediction").
    setMetricName("accuracy")

// e.- Finalmente nos regresa un valor entre 1 y 0 que es la precision del modelo, entre mas
// cercano a 1 mejor la prediccion.
println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")