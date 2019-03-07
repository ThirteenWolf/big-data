// 1
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

// 2
val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv")

// 3
for(column <- df.columns){
    println(column)
}

// 4
df.printSchema()

// 5
for(x <- df.head(5)){
    println(x)
}

// 6
df.describe().show()

// 7
val df2 = df.withColumn("HV Ratio", $"High" + $"Low")

// 8
df.orderBy($"High".desc).show(1)

// 9
// Es al precio al que cerraron las acciones de Netflix ese dia

// 10
df.select(min($"Volume")).show()

df.select(max($"Volume")).show()

// 11
// a
df.filter($"Close"<600).select(count("*").as("Days")).show()

// b
val total = df.count()
df.filter($"High">500).select(((count("*")*100)/total).as("%")).show()

// c
df.select(corr($"High", $"Volume").show()

// d
val df2 = df.withColumn("Year", year($"Date"))

val dfmax = df2.groupBy("Year").max()

dfmax.select($"Year", $"max(High)").orderBy("Year").show()

// e
val df3 = df.withColumn("Month", month($"Date"))

val dfaverage = df3.groupBy("Month").mean()

dfaverage.select($"Month", $"avg(Close)").orderBy($"Month").show()