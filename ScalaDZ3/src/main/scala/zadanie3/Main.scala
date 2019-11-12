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
  println(defineAnimal(Creature(225, 2, 2)))

  def defineAnimal(animal: Creature) = {
    animal.coatLength match {
      case 0 =>
        if ((0 to 2).contains(animal.weight))
          if ((2 to 4).contains(animal.length)) "Black mamba" else "Alien"
        else if ((200 to 550).contains(animal.weight))
          if ((2 to 4).contains(animal.length)) "Nil crocodile" else "Alien"
        else if ((5 to 7).contains(animal.length)) "Elephant" else "Alien"
      case _ =>
        animal.length match {
          case 0 => if ((0 to 1).contains(animal.weight)) "Meerkat" else "Alien"
          case _ => if (animal.length < 3) "Lion" else "Elephant"
        }
    }
  }
}

case class Creature(weight: Int, length: Int, coatLength: Int) {

}


