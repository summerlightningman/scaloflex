package zadanie1


import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {
    feed()
  }

  def feed(): Unit = {
    val names = Array("Gobbs", "Lokk", "Aristotle", "Sokrat", "Archimed")
    val spoons = Array[Boolean](false, false, false, false, false)
    val philosophers = new Array[Filosof](5)
    for (i <- philosophers.indices) philosophers(i) = Filosof(names(i), i, if (i == philosophers.length - 1) 0 else i + 1)

    philosophers.foreach((philosopher: Filosof) => {
      val toEat = new Thread(new Runnable {
        override def run(): Unit = {
          println(s"${philosopher.name} will take a left spoon ${philosopher.left}")
          spoons(philosopher.left).synchronized {
            println(s"${philosopher.name} has took a left spoon ${philosopher.left}")
            spoons(philosopher.right).synchronized {
              println(s"${philosopher.name} has took a right spoon ${philosopher.right}")
              println(s"${philosopher.name} is eating...")
              Thread.sleep(1500)
            }
            println(s"${philosopher.name} has put a right spoon ${philosopher.right}")
          }
          println(s"${philosopher.name} has put a left spoon ${philosopher.left}")
          println(s"${philosopher.name} ate!")
        }
      }).start()
    })

  }
}