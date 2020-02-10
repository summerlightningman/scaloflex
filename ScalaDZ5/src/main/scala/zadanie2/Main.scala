package zadanie2

object Main {
  def main(args: Array[String]): Unit = {
    3232.prettyPrint
  }

  implicit class Pretty(number: Int) {
    implicit def prettyPrint: Unit = {
      val count = number.toString.length
      println(s"-${"-" * count}-\n|$number|\n-${"-" * count}-")
    }

  }

}
