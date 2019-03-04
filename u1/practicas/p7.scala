import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("CitiGroup2006_2008")


// Añade un numero especifico de meses a todas las fechas de la columna
df.select(add_months($"Date", 2)).show()

// Devuelve el numero de dias que han pasado desde la fecha de la columna hasta la fecha actual
df.select(datediff(current_date(), $"Date")).show()

// Devuelve un numero que representa el dia del mes de la fecha
df.select(dayofmonth($"Date")).show()

// Devuelve un numero que representa el dia de la semana de la fecha
df.select(dayofweek($"Date")).show()

// Devuelve un numero que representa el dia del año de la fecha
df.select(dayofyear($"Date")).show()

// Devuelve la hora de la fecha
df.select(hour($"Date")).show()

// Devuelve el ultimo dia del mes al que pertenece la fecha
df.select(last_day($"Date")).show()

// Devuelve el minuto de la fecha
df.select(minute($"Date")).show()

// Devuelve un numero que representa el mes de la fecha
df.select(month($"Date")).show()

// Devuelve el numero de meses entre 2 fechas
df.select(months_between(current_date(), $"Date")).show()

// Devuelve el número de meses entre 2 fechas, redondeando el numero a 8 digitos
df.select(months_between(current_date(), $"Date", true)).show()

// Devuelve la siguiente fecha despues de la fecha que cae en el dia que se especifica 
df.select(next_day($"Date", "Sunday")).show()

// Devuelve el año de la fecha
df.select(year($"Date")).show()

// Devuelve el numero de semana en el año de la fecha
df.select(weekofyear($"Date")).show()

// Devuelve el segundo de la fecha
df.select(second($"Date")).show()

// Devuelve el cuarto del año de la fecha
df.select(quarter($"Date")).show()