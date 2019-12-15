package main.scala.zadanie4

import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


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
    .collect.map {
    case line =>
      val fmt = DateTimeFormat.forPattern("yyyy-MM-dd")
      Row(line(0), line(1), fmt.print(new DateTime(line(1)).plusDays(line(0).toString.toInt)))
  }

  val cols = new StructType()
    .add("number_of_days", IntegerType)
    .add("date", StringType)
    .add("future_date", StringType)

  val result = spark.createDataFrame(spark.sparkContext.parallelize(df), cols)
  result.show()
}
