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

val df = spark.read.option("header", "false").schema(schemaStruct)csv("Iris.csv")

val labelIndexer = new StringIndexer().
    setInputCol("label").
    setOutputCol("labelIndex").
    fit(df)

val assembler = new VectorAssembler().
    setInputCols(Array("sepalLength", "sepalWidth", "petalLength", "petalWidth")).
    setOutputCol("features")

val Array(trainingData, testData) = df.randomSplit(Array(0.7, 0.3))

val layers = Array[Int](4, 4, 4, 3)

val mp = new MultilayerPerceptronClassifier().
    setLayers(layers).
    setLabelCol("labelIndex").
    setFeaturesCol("features").
    setPredictionCol("prediction").
    setBlockSize(128).
    setSeed(1234L).
    setMaxIter(100)

val stages = Array(labelIndexer, assembler, mp)

val pipeline = new Pipeline().setStages(stages)

val model = pipeline.fit(trainingData)

val result = model.transform(testData)

val predictionAndLabels = result.select("prediction", "labelIndex")

val evaluator = new MulticlassClassificationEvaluator().
    setLabelCol("labelIndex").
    setPredictionCol("prediction").
    setMetricName("accuracy")

println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")