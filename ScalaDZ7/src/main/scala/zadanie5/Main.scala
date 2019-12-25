package main.scala.zadanie5


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

// Задание: Finding Most Populated Cities Per Country
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-Finding-Most-Populated-Cities-Per-Country.html

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie5").getOrCreate()
  val populations = spark.read.format("csv").option("header", "true").load("populations.csv")
  val city = populations.groupBy("name").agg(min("population").as("c_population"))

  populations
    .groupBy("country")
    .agg(max("population").as("population"))
    .join(city, col("population") === city("c_population"))
    .select(col("name"), col("country"), col("c_population").as("population"))
    .show()

}
