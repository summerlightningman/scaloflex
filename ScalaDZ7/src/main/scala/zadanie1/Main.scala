package main.scala.zadanie1

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

// Задание: Finding 1st and 2nd Bestsellers Per Genre
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-Finding-1st-and-2nd-Bestsellers-Per-Genre.html

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie1").getOrCreate()

  val df = spark.read.format("csv").option("header", "true").load("books.csv")
  val bestsellers = df.collect().groupBy(line => line(2))
    .map {
    case line => Row(line._2.sortBy(line => line.getString(3)).take(2))
  }.toSeq
  val cols = new StructType()
    .add("id", StringType)
    .add("title", StringType)
    .add("genre", StringType)
    .add("quantity", StringType)
  val result = spark.createDataFrame(spark.sparkContext.parallelize(bestsellers), cols)
  result.show()
}
