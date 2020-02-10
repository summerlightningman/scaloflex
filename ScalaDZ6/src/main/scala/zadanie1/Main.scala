package zadanie1

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object Main extends App {
  val philosophers: Array[String] = Array(
    "Archimed",
    "Smyshlyaev",
    "Lokk",
    "Gobbs",
    "Plato",
  )

  val seizedDishes: Array[Boolean] = Array(false, false, false, false, false)

  philosophers.foreach(goEat)

  def goEat(philosopher: String): Future[Unit] = {
    Future {
      println(f"$philosopher is going to eat")
      val index, left = philosophers.indexOf(philosopher)
      val right = if (philosopher == philosophers.last) 0 else index + 1
      println(f"\t$philosopher will take left $left dish")
      seizedDishes(left).synchronized {
        println(f"\t\t$philosopher has took left $left dish")
        println(f"\t\t\t$philosopher will take right $right dish")
        seizedDishes(right).synchronized {
          println(f"\t\t\t\t$philosopher has took right $right dish")
          println(f"$philosopher is eating")
          Thread.sleep(1000)
          println(f"\t\t\t\t$philosopher will put right $right dish")
        }
        println(f"\t\t\t$philosopher has put right $right dish")
        println(f"\t\t$philosopher will put left $left dish")
      }
      println(f"\t$philosopher has put right $left dish")
      println(f"$philosopher has ate")

    }
  }

  Thread.sleep(6500)

}


