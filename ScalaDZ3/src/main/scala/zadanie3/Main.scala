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
    val w = animal.weight;
    val l = animal.length;
    val c = animal.coatLength;

    if ((200 to 250).contains(w) && (1 to 3).contains(l) && (1 to 30).contains(c))
      "Лев"
    else if ((0 to 2).contains(w) && (1 to 3).contains(l) && (1 to 30).contains(c))
      "Чёрная мамба"
    else if (w > 5000 && (5 to 7).contains(l) && (0 to 15).contains(c))
      "Слон"
    else if ((200 to 550).contains(w) && (2 to 4).contains(l) && c == 0)
      "Нильский крокодил"
    else if ((0 to 1).contains(w) && l == 0 && (2 to 5).contains(c))
      "Сурикат"
    else if ((0 to 1).contains(w) && (0 to 2).contains(l) && (0 to 3).contains(c))
      "Киви"
    else
      "Инопланетянин"
  }

  def defineAllAnimals(animal: Creature*): Unit = {
    for (i <- 0 until animal.length) {
      println(defineAnimal(animal(i)))
    }
  }
}

case class Creature(weight: Int, length: Int, coatLength: Int) {

}


