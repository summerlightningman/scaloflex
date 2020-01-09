package zadanie1

import java.io.{File, PrintWriter}

import akka.actor.ActorRef
import zadanie1.ShuffleActor.ShuffleData


object Main {


  def main(args: Array[String]): Unit = {


    val inputPath = s"${System.getProperty("user.dir")}/src/main/resources/source"

    val sourceList: Seq[File] = {
      val dir = new File(inputPath)
      if (dir.exists()) {
        dir.listFiles.filter(_.isFile).toSeq
      } else {
        println("This directory doesn't exists")
        Seq.empty[File]
      }
    }

    def myFilter(mappers: Seq[ActorRef])(file: File): Unit = {
      val source = io.Source.fromFile(file)
      var i = 0
      for (line <- source.getLines) {
        val cols = line.split(",").map(_.trim).toSeq
        try
          mappers(i) ! Car(cols(2), cols(3), cols(4))
        catch {
          case _: Exception =>
        }
        i = if (i == mappers.length) 0 else i + 1
      }
      source.close
    }

    def myMap(shuffle: ActorRef)(brand: String, model: String, year: String): Unit = {
      val car = s"${brand} ${model} (${year})"
      shuffle ! ShuffleData(car, 1)
    }

    def myReduce(index: Int, outputPath: String)(reducedData: Array[(String, Int)]): Unit = {
      val writer = new PrintWriter(s"${outputPath}/cars-${index}.csv")
      reducedData.foreach {
        case (key, value) => writer.write(s"${key},${value}\n")
      }
      writer.close()
    }

    val stream: Stream = Stream.filter(10)(myFilter).map(3)(myMap).reduce(4)(myReduce)

    stream.run(sourceList)
  }

  case class Car(brand: String, model: String, year: String)

}

