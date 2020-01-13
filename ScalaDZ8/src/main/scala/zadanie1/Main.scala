package zadanie1

import java.io.File

import akka.actor.ActorSystem

import scala.io.Source


object Main extends Types {
  override type Line = String

  def main(args: Array[String]): Unit = {

    val sourceList: Seq[String] = {
      val file = new File(s"${System.getProperty("user.dir")}/src/main/resources/source/cars.csv")
      if (file.exists) {
        val source = Source.fromFile(file)
        val lines = source.getLines().toList
        source.close()
        lines
      } else {
        println("This file doesn't exists")
        Seq.empty[String]
      }
    }

    def myFilter(line: String): Boolean = line.split(",").toSeq.length == 7

    def myMap(line: String): (String, Int) = {
      val info = line.split(",").map(_.trim).toSeq.map(_.toLowerCase.capitalize)
      (s"${info(2)} ${info(3)} (${info(4)})", 1)
    }

    def myReduce(dataList: Seq[(String, Int)], data: (String, Int)): Seq[(String, Int)] = {
      (dataList :+ data).groupBy(_._1).map {
        case (key, values) => (key, values.map(_._2).toList.sum)
      }.toSeq
    }

    val system = ActorSystem("cars")

    val stream = Stream.filter(10)(myFilter).map(3)(myMap).reduce(4)(myReduce)

    println(stream.run(sourceList, system))

  }


}

