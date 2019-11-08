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
    println(s"В %s году, Петром I была проведена реформа 'О %i'".format(name, year))
  }

  override def goWar(name: String, year: Int, country: String): Int = {
    val deaths = Random.nextInt(1000000);
    println(s"В %n, Пётр I провёл Российскую империю через %s войну с %s, унёсшую жизни около %i человек!".format(year, name, country, deaths))
    deaths
  }
}

case class IvanFedorovich() extends King {
  override def captureNewTerritory(name: String): Unit = {
    val s = Random.nextFloat() * 50;
    println(s"Территория земель $name площадью $s гектар была недавно захвачена!")
  }

  override def killBrother(name: String): String = {
    println(s"Брат $name убит! Вы - царь.")
    name
  }

  override val name: String = "Иоанн"
}

