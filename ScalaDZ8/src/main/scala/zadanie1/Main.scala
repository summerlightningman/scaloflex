package zadanie1

import java.io.File


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

    def myFilter(line: Int): Boolean = line > 0

    // #####
    def myMap(line: Int): (String, Int) = {
      line.toString -> line * 10
    }

    // #####
    def myReduce(x1: Int , x2: Int): Int = {
      x1 + x2
    }

    val list = List(1, 2, 3)

    val stream = Stream.filter(10)(myFilter).map(3)(myMap).reduce(4)(myReduce)

    stream.run(list)


  }


  override type Line = String
}

