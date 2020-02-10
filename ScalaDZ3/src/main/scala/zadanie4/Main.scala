package zadanie4

import scala.io.Source

object Main extends App {
  val source = Source.fromFile("file.txt")
  val lines = try source.getLines().toList finally source.close()

  val data = lines.map((line: String) =>
    (line.split(";")(0).toInt, line.split(";")(1).toDouble))
    .groupBy(_._1)

  val groupped = data.map {
    case (typ, count) => (typ, count.map {
      case (_, j: Double) => j
    })
  }

  println(s"Common package was delivered in ${groupped(1).length} units")
  println(s"Uncommon package was delivered in ${groupped(2).length} units")

  val common = Common(groupped(1).sum)
  val uncommon = Uncommon(groupped(2).sum)

  println(s"Common package weight equals ${common.pack()} weight units")
  println(s"Common package weight equals ${uncommon.pack()} weight units")
}
