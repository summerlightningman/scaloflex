package zadanie2

object Main {
  def main(args: Array[String]): Unit = {


    race(432, StreetRacer(), Motocycler(), DragRacer(), Sprinter())

    def race(distance: Int, racers: Runner*) = {
      var times = List[Int]()
      var names = List[String]()

      racers.foreach((racer: Runner) => {
        times +:= racer.run(distance)
        names +:= racer.name
      })
      val results = names.zip(times)
      val places = results.sortBy(_._2)

      for (i <- places.indices) {
        val name =places(i)._1
        val time =places(i)._2

        val min = time / 60
        val sec = time % 60

        println(s"${i+1}-e место занимает $name с результатом $min:$sec")
      }
    }
  }

  trait Runner {
    val name: String

    def run(distance: Int): Int
  }

  case class StreetRacer() extends Runner {
    override val name: String = "Dimas"

    override def run(distance: Int): Int = distance / 3
  }

  case class Motocycler() extends Runner {
    override val name: String = "Eugene"

    override def run(distance: Int): Int = distance / 3 + 2
  }

  case class DragRacer() extends Runner {
    override val name: String = "Yarik"

    override def run(distance: Int): Int = distance + 5
  }

  case class Sprinter() extends Runner {
    override val name: String = "Serega"

    override def run(distance: Int): Int = (distance / 3) * 2 + distance / 2 * 3
  }

}
