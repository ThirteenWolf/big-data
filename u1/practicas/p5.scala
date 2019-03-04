import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Sales.csv")

// Devuelve una cuenta de registros distintos con un margen de error especificado
df.select(approx_count_distinct("Sales", 0.2)).show()

// Devuelve el promedio de una columna
df.select(avg("Sales")).show()

// Devuelve una lista
df.select(collect_list("Sales")).show()

// Devuelve la correlacion de 2 columnas
df.select(corr("Sales", "Sales")).show()

// Devuelve el número de registros en la columna
df.select(count("Sales")).show()

// Devuelve el primer registro en la columna
df.select(first("Sales")).show()

// Devuelve el primer registro en la columna ignorando nulos
df.select(first("Company", true)).show()

// Devuelve la kurtosis de los valores en una columna
df.select(kurtosis("Sales")).show()

// Devuelve el último valor de una columna
df.select(last("Company")).show()

// Devuelve el último valor de una columna ignorando nulos
df.select(last("Company", true)).show()

// Devuelve el valor máximo de una columna
df.select(max("Sales")).show()

// Devuelve la media de una columna
df.select(mean("Sales")).show()

// Devuelve el valor mínimo de una columna
df.select(min("Sales")).show()

// Devuelve un valor que representa la falta de simetria de una columna
df.select(skewness("Sales")).show()

// Devuelve la desviación estándar de la poblacion
df.select(stddev_pop("Sales")).show()

// Devuelve la desviacion estandar de la muestra
df.select(stddev_samp("Sales")).show()

// Devuelve la suma de una columna
df.select(sum("Sales")).show()

// Devuelve la varianza de la poblacion
df.select(var_pop("Sales")).show()

// Devuelve la varianza de una muestra
df.select(var_samp("Sales")).show()