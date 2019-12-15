package main.scala.zadanie1

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

// Задание: Finding 1st and 2nd Bestsellers Per Genre
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-Finding-1st-and-2nd-Bestsellers-Per-Genre.html

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie1").getOrCreate()

  val df = spark.read.format("csv").option("header", "true").load("books.csv")
  df.orderBy(desc("quantity")).rdd
    .groupBy(line => line.getString(2))
    .map(line => line._2.take(2))
    .foreach(println)
}
