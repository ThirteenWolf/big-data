// Importamos e iniciamos SparkSession
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().appName("PCA_Example").getOrCreate()

// Cargamos los datos del csv
val data = spark.read.option("header","true").option("inferSchema","true").format("csv").load("Cancer_Data")

// Imprimimos el Schema de los datos
data.printSchema()


// Importamos transformadores de la libreria ml para preparar los datos
import org.apache.spark.ml.feature.{PCA,StandardScaler,VectorAssembler}
import org.apache.spark.ml.linalg.Vectors

// Creamos un Array con los nombres de las columnas
val colnames = (Array("mean radius", "mean texture", "mean perimeter", "mean area", "mean smoothness",
"mean compactness", "mean concavity", "mean concave points", "mean symmetry", "mean fractal dimension",
"radius error", "texture error", "perimeter error", "area error", "smoothness error", "compactness error",
"concavity error", "concave points error", "symmetry error", "fractal dimension error", "worst radius",
"worst texture", "worst perimeter", "worst area", "worst smoothness", "worst compactness", "worst concavity",
"worst concave points", "worst symmetry", "worst fractal dimension"))

// Creamos un VectorAssembler que comprimira las columnas en un Vector
val assembler = new VectorAssembler().setInputCols(colnames).setOutputCol("features")

// Tomamos los features de los datos
val output = assembler.transform(data).select($"features")

// Inicializamos un StandardScaler para Normalizar los datos
val scaler = (new StandardScaler()
  .setInputCol("features")
  .setOutputCol("scaledFeatures")
  .setWithStd(true)
  .setWithMean(false))

// Se entrena el escalador (calcula Desviacion estandar, etc)
val scalerModel = scaler.fit(output)

// Se aplica la normalizacion a los datos
val scaledData = scalerModel.transform(output)

// Inicializamos un PCA(Principal Component Analysis) para que reduzca todas nuestros features a 4 principales
val pca = (new PCA()
  .setInputCol("scaledFeatures")
  .setOutputCol("pcaFeatures")
  .setK(4)
  .fit(scaledData))

// Aplicamos el PCA a los datos
val pcaDF = pca.transform(scaledData)

// Seleccionamos e imprimimos los resultados
val result = pcaDF.select("pcaFeatures")
result.show(false)

result.head(1)
