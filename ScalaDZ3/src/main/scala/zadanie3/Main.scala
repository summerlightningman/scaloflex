package zadanie3

object Main extends App {
  val lion = Creature(201, 3, 30)
  val mamba = Creature(0, 2, 24)
  val elephant = Creature(100000000, 6, 12)
  val crocodile = Creature(300, 3, 0)
  val qiwi = Creature(0, 0, 0)
  val alien = Creature(9999, 9999, 9999)

  defineAllAnimals(lion, mamba, elephant, crocodile, qiwi, alien)

  def defineAnimal(animal: Creature) = {
    (animal) match {
      case Creature(_, _, _) => "elephant"
      case _ => "privet"
    }
  }

  def defineAllAnimals(animal: Creature*): Unit = {
    for (i <- 0 until animal.length) {
      println(defineAnimal(animal(i)))
    }
  }
}

case class Creature(weight: Int, length: Int, coatLength: Int) {

}


