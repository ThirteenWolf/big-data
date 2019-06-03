import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.mllib.evaluation.{BinaryClassificationMetrics, MulticlassMetrics}
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler, OneHotEncoderEstimator}

val data = spark.read.format("csv").
            option("sep", ";").
            option("inferSchema", "true").
            option("header", "true").
            load("bank-additional-full.csv").
            withColumnRenamed("y", "label").
            withColumnRenamed("emp.var.rate", "empVarRate").
            withColumnRenamed("cons.price.idx", "consPriceIdx").
            withColumnRenamed("cons.conf.idx", "consConfIdx").
            withColumnRenamed("nr.employed", "nrEmployed")

val jobIndexer = new StringIndexer().
        setInputCol("job").
        setOutputCol("jobIndexed").
        fit(data)

val maritalIndexer = new StringIndexer().
        setInputCol("marital").
        setOutputCol("maritalIndexed").
        fit(data)

val educationIndexer = new StringIndexer().
        setInputCol("education").
        setOutputCol("educationIndexed").
        fit(data)

val defaultIndexer = new StringIndexer().
        setInputCol("default").
        setOutputCol("defaultIndexed").
        fit(data)

val housingIndexer = new StringIndexer().
        setInputCol("housing").
        setOutputCol("housingIndexed").
        fit(data)

val loanIndexer = new StringIndexer().
        setInputCol("loan").
        setOutputCol("loanIndexed").
        fit(data)

val contactIndexer = new StringIndexer().
        setInputCol("contact").
        setOutputCol("contactIndexed").
        fit(data)

val monthIndexer = new StringIndexer().
        setInputCol("month").
        setOutputCol("monthIndexed").
        fit(data)

val dayOfWeekIndexer = new StringIndexer().
        setInputCol("day_of_week").
        setOutputCol("dayOfWeekIndexed").
        fit(data)

val pOutcomeIndexer = new StringIndexer().
        setInputCol("poutcome").
        setOutputCol("pOutcomeIndexed").
        fit(data)

val encoder = new OneHotEncoderEstimator().
        setInputCols(Array("jobIndexed", "maritalIndexed", "educationIndexed", "defaultIndexed", "housingIndexed", "loanIndexed", "contactIndexed", "monthIndexed", "dayOfWeekIndexed", "pOutcomeIndexed")).
        setOutputCols(Array("jobEncoded", "maritalEncoded", "educationEncoded", "defaultEncoded", "housingEncoded", "loanEncoded", "contactEncoded", "monthEncoded", "dayOfWeekEncoded", "pOutcomeEncoded"))

val assembler = new VectorAssembler().
        setInputCols(Array("age", "jobEncoded", "maritalEncoded", "educationEncoded", "defaultEncoded", "housingEncoded", "loanEncoded", "contactEncoded", "monthEncoded", "dayOfWeekEncoded", "duration", "campaign", "pdays", "previous", "pOutcomeEncoded", "empVarRate", "consPriceIdx", "consConfIdx", "euribor3m", "nrEmployed")).
        setOutputCol("features")

val labelIndexer = new StringIndexer().
        setInputCol("label").
        setOutputCol("indexedLabel").
        fit(data)

val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

val lr = new LogisticRegression().
            setMaxIter(100).
            setRegParam(0.1).
            setElasticNetParam(0.1).
            setLabelCol("indexedLabel").
            setFeaturesCol("features").
            setStandardization(true)

val labelConverter = new IndexToString().
    setInputCol("prediction").
    setOutputCol("predictedLabel").
    setLabels(labelIndexer.labels)

val pipeline = new Pipeline().
    setStages(Array(jobIndexer, maritalIndexer, educationIndexer, defaultIndexer, housingIndexer, loanIndexer, contactIndexer, monthIndexer, dayOfWeekIndexer, pOutcomeIndexer, encoder, assembler, labelIndexer, lr, labelConverter))

val lrModel = pipeline.fit(trainingData)

val predictions = lrModel.transform(testData)

val evaluator = new MulticlassClassificationEvaluator().
    setLabelCol("indexedLabel").
    setPredictionCol("prediction").
    setMetricName("accuracy")

val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${(1.0 - accuracy)}")


val predictionsAndLabels = predictions.select($"prediction", $"indexedLabel").as[(Double, Double)].rdd

val multimetrics = new MulticlassMetrics(predictionsAndLabels)
println("Confusion Matrix: ")
println(multimetrics.confusionMatrix)

println("F-Measure: ")
multimetrics.labels.foreach{ (l) =>
        println(s"$l: " + multimetrics.fMeasure(l))
}

println("Weighted F-Measure: " + multimetrics.weightedFMeasure)

println("True positive rate: ")
multimetrics.labels.foreach{ (l) =>
        println(s"$l: " + multimetrics.truePositiveRate(l))
}

println("Weighted True positive rate: " + multimetrics.weightedTruePositiveRate)

println("False positive rate: ")
multimetrics.labels.foreach{ (l) =>
        println(s"$l: " + multimetrics.falsePositiveRate(l))
}

println("Weighted False positive rate: " + multimetrics.weightedFalsePositiveRate)


println("Precision: ")
multimetrics.labels.foreach{ (l) =>
        println(s"$l: " + multimetrics.precision(l))
}

println("Weighted Precision: " + multimetrics.weightedPrecision)

println("Recall: ")
multimetrics.labels.foreach{ (l) =>
        println(s"$l: " + multimetrics.recall(l))
}

println("Weighted Recall: " + multimetrics.weightedRecall)

val metrics = new BinaryClassificationMetrics(predictionsAndLabels)

val areaUnderPR = metrics.areaUnderPR
println(s"Area Under PR: $areaUnderPR")

val areaUnderROC = metrics.areaUnderROC
println(s"Area Under ROC: $areaUnderROC")
