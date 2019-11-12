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

  println(s"Обычного груза было доставлено ${groupped(1).length} единиц")
  println(s"Ценного груза было доставлено ${groupped(2).length} единиц")

  val common = Common(groupped(1).sum)
  val uncommon = Uncommon(groupped(2).sum)

  println(s"Масса обычного груза равна ${common.pack()} единицам массы")
  println(s"Масса ценного груза равна ${uncommon.pack()} единицам массы")
}
