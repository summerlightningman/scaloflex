package main.scala.zadanie2

import org.apache.spark.sql.types.{IntegerType, StructType}
import org.apache.spark.sql.{Row, SparkSession}

// Задание: Multiple Aggregations
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-Multiple-Aggregations.html

object Main extends App {

  val spark = SparkSession.builder().master("local[*]").appName("zadanie2").getOrCreate()
  val arr = (0 until 5).map(value => Row(value, value % 2))

  val arraySchema = new StructType()
    .add("id", IntegerType)
    .add("group", IntegerType)
  val df = spark.createDataFrame(spark.sparkContext.parallelize(arr), arraySchema)
  df.createOrReplaceTempView("nums")
  val sql = spark.sql("SELECT DISTINCT " +
    "group," +
    "(SELECT max(id) FROM nums WHERE group = n.group) as `max_id`," +
    "(SELECT min(id) FROM nums WHERE group = n.group) as `min_id` " +
    "FROM nums n")
  sql.show()

}
