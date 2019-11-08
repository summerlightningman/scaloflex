package zadanie4

import java.io.{File, FileInputStream}
import java.util.Scanner

object Main extends App {
  val file = new Scanner(new File("file.txt"))
  var total = 0d
  while (file.hasNextLine) {
    val pakage = file.nextLine()
    val cargo = pakage.split(";")
    val pack = cargo(0) match {
      case "1" =>
        Common(cargo(1).toDouble)
      case "2" =>
        Uncommon(cargo(1).toDouble)
    }
    total += pack.pack()
  }
  println(s"Total weight = $total")


}
