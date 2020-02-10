package zadanie1

object Main {
  def main(args: Array[String]): Unit = {
    val solomon = Salmon
//    val maineCoon = MaineCoon
//    val python = Python

    feedFish(solomon)
    //    feedFish(maineCoon) // Error
    //    feedFish(python) // Error
  }

  def feedFish[F >: Fish](f: F): Unit = {
    println(s"Fish $f was fed")
  }

  case class Salmon() extends Fish

  case class Crucial() extends Fish

  case class Shark() extends Fish

  case class Tiger() extends Cat

  case class Lion() extends Cat

  case class MaineCoon() extends Cat

  case class Cobra() extends Snake

  case class Python() extends Snake

}
