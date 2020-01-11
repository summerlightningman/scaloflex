package zadanie1

import java.io.File


object Main extends Types {

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

    def myFilter(line: Line): Boolean = line.split(",").toSeq.length == 6

    def myMap(line: String): Data = {
      val info = line.split(",").map(_.trim).toSeq
      Data(s"${info(2)} ${info(3)} (${info(4)})", 1)
    }

    def myReduce(dataList: Seq[Data], data: Data): Seq[Data] = {
      (dataList :+ data).groupBy(_.key).map {
        case (key, values) => Data(key, values.map(_.value).toList.sum)
      }.toSeq
    }

    val stream = Stream.filter(10)(myFilter).map(3)(myMap).reduce(4)(myReduce)

    stream.run(sourceList)


  }

  case class Car(brand: String, model: String, year: String)

}

