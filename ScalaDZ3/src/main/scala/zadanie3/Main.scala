package zadanie3

object Main extends App {
  val lion = Creature(201, 3, 30)
  val mamba = Creature(0, 2, 24)
  val elephant = Creature(100000000, 6, 12)
  val crocodile = Creature(300, 3, 0)
  val qiwi = Creature(0, 0, 0)
  val alien = Creature(9999, 9999, 9999)

  println(defineAnimal(alien))
  println(defineAnimal(crocodile))
  println(Creature(225, 2, 2))

  def defineAnimal(animal: Creature) = {
    (animal) match {
      case Creature(225, 2, 2) => "Lion"
      case Creature(1, 3, 0) => "Black mamba"
      case Creature(999999, 6, 7) => "Elephant"
      case Creature(375, 3, 0) => "Nil crocodile"
      case Creature(1, 0, 4) => "Meerkat"
      case _ => "Alien"
    }
  }
}

case class Creature(weight: Int, length: Int, coatLength: Int) {

}


