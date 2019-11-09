package zadanie1

import scala.util.Random

object Main extends App {

}

trait Ruler {
  val name: String
}

trait King extends Ruler {
  def captureNewTerritory(name: String): Unit

  def killBrother(name: String): String
}

trait Emperor extends Ruler {
  def goReforms(name: String, year: Int): Unit

  def goWar(name: String, year: Int, country: String): Int
}

abstract class RomanovsDynasty {
  val count: Int

  val beganYear: Int
}

case class PyotrFirst() extends RomanovsDynasty with Emperor {
  override val count: Int = 1
  override val beganYear: Int = 1682
  override val name: String = "Pyotr"

  override def goReforms(name: String, year: Int): Unit = {
    println(s"В $year году, Петром I была проведена реформа 'О $name'"
  }

  override def goWar(name: String, year: Int, country: String): Int = {
    val deaths = Random.nextInt(1000000);
    println(s"В $year году, Пётр I провёл Российскую империю через $name войну с $country, унёсшую жизни около $deaths человек!")
    deaths
  }
}

case class IvanFedorovich() extends King {
  override def captureNewTerritory(name: String): Unit = {
    val S = Random.nextFloat() * 50;
    println(s"Территория земель $name площадью $S гектар была недавно захвачена!")
  }

  override def killBrother(name: String): String = {
    println(s"Брат $name убит! Вы - царь.")
    name
  }

  override val name: String = "Иоанн"
}

