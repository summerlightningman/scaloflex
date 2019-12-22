package main.scala.zadanie5


import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._

// Задание: Finding Most Populated Cities Per Country
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-Finding-Most-Populated-Cities-Per-Country.html

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie5").getOrCreate()
  val populations = spark.read.format("csv").option("header", "true").load("populations.csv")
  //
}
