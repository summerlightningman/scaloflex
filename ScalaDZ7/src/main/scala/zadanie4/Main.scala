package main.scala.zadanie4

import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

// Задание: How to add days (as values of a column) to date?
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-How-to-add-days-as-values-of-a-column-to-date.html

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie1").getOrCreate()

  import spark.implicits._

  val df = Seq(
    (0, "2016-01-1"),
    (1, "2016-02-2"),
    (2, "2016-03-22"),
    (3, "2016-04-25"),
    (4, "2016-05-21"),
    (5, "2016-06-1"),
    (6, "2016-03-21")
  ).toDF("number_of_days", "date")
  df.withColumn("future", expr("date_add(date,number_of_days)")).show()
}
