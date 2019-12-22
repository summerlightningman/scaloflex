package main.scala.zadanie5


import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{Row, SparkSession}

// Задание: Finding Most Populated Cities Per Country
// Ссылка: http://blog.jaceklaskowski.pl/spark-workshop/exercises/spark-sql-exercise-Finding-Most-Populated-Cities-Per-Country.html

object Main extends App {
  val spark = SparkSession.builder().master("local[*]").appName("zadanie5").getOrCreate()
  val populations = spark.read.format("csv").option("header", "true").load("populations.csv")
  val strToNum = populations
    .collect()
    .map {
    case line => Row(line(0), line(1), line(2).toString.replaceAll(" ", "").toInt)
  }
  val cols = new StructType()
    .add("name", StringType)
    .add("country", StringType)
    .add("population", IntegerType)

  val df = spark.createDataFrame(spark.sparkContext.parallelize(strToNum), cols)
    .createOrReplaceTempView("nums")

  val sql = spark.sql("SELECT " +
    "first(name) as name, " +
    "country, " +
    "first(population) as population " +
    "FROM nums " +
    "GROUP BY country " +
    "ORDER BY population DESC")
  sql.show()
}