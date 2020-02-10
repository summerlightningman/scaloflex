package zadanie2

import scala.io.StdIn._

object Main extends App {
  val runners = List[Runner](
    AmericanRunner("Eugene"),
    BritishRunner("John"),
    RussianRunner("Dmitry"),
    JapaneseRunner("Haruki")
  )

  println("Input number")
  val factor = readDouble

  runners.map(runner => runner.name -> runner.run(factor))
    .sortBy(-_._2)
    .foreach(runner => println(s"Runner ${runner._1} has finished with result ${runner._2}"))
}
