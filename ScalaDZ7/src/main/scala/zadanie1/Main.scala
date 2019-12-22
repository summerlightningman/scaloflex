package main.scala.zadanie1

import org.apache.spark.sql.SparkSession

// Задание: Finding 1st and 2nd Bestsellers Per Genre
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-Finding-1st-and-2nd-Bestsellers-Per-Genre.html

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie1").getOrCreate()

  val df = spark.read.format("csv").option("header", "true").load("books.csv")
    .createOrReplaceTempView("book")
  val sql = spark.sql("SELECT * FROM (SELECT id, title, genre, quantity, rank() OVER (PARTITION BY genre ORDER BY quantity DESC) as rank FROM book) WHERE rank <= 2")
  .show()
\
}
