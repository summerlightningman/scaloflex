package main.scala.zadanie3

import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{Row, SparkSession}

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie1").getOrCreate()

  val cities = spark.read.format("csv").option("header", "true").load("cities.csv")
  val result = cities.collect.map {
    line => Row(line(0), line(1), line(2), line(1).toString.toUpperCase)
  }
  val cols = new StructType()
    .add("id", StringType)
    .add("city", StringType)
    .add("country", StringType)
    .add("upper_city", StringType)
  val df = spark.createDataFrame(spark.sparkContext.parallelize(result), cols)
  df.show()
}
