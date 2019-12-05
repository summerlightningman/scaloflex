package zadanie1

import org.scalatest._

class GoTest extends FlatSpec with Matchers {
  val names = Array("Gobbs", "Lokk", "Aristotle", "Sokrat", "Archimed")
  val spoon = Array[Boolean](false, false, false, false, false)
  val philosophers = new Array[Filosof](5)
  for (i <- philosophers.indices) philosophers(i) = Filosof(names(i), i, if (i == philosophers.length - 1) 0 else i + 1)

  philosophers.foreach((p: Filosof) => {
    val toEat = new Thread(new Runnable {
      override def run(): Unit = {
        println(s"${p.name} want to take a left spoon ${p.left}")
        spoon(p.left).synchronized {

          s"Left spoon (${p.left}) for ${p.name}" should "be free" in {
            spoon(p.left) should be(false)
          }

          spoon(p.left) = true
          println(s"${p.name} has took a left spoon (${p.left})")

          s"Left spoon (${p.left})" should "be busy" in {
            spoon(p.left) should be(true)
          }

          println(s"${p.name} want to take a right spoon (${p.right})")
          spoon(p.right).synchronized {
            s"Right spoon (${p.right}) for ${p.name}" should "be free" in {
              spoon(p.right) should be(false)
            }
            spoon(p.right) = true

            println(s"${p.name} has took a right spoon (${p.right})")

            spoon(p.left) should be(true)
            spoon(p.right) should be(true)
            println(s"${p.name} is eating...")
            Thread.sleep(1500)
          }

          spoon(p.left) should be(true)
          spoon(p.right) should be(true)

          spoon(p.right) = false
          println(s"${p.name} has put a right spoon (${p.right})")
          s"Right spoon (${p.right}) for ${p.name}" should "be free" in {
            spoon(p.right) should be(false)
          }
        }
        spoon(p.left) = false

        println(s"${p.name} has put a left spoon (${p.left})")
        s"Left spoon (${p.left})" should "be free" in {
          spoon(p.left) should be(false)
        }

        println(s"${p.name} ate!")
      }
    }).start()
  })
}
