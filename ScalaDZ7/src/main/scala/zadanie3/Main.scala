package main.scala.zadanie3

import org.apache.spark.sql.types.{StringType, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._

// Задание: Using upper Standard Function
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-Using-upper-Standard-Function.html

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie1").getOrCreate()

  val cities = spark.read.format("csv").option("header", "true").load("cities.csv")
  cities.withColumn("upper_city", upper(col("city"))).show()
}
