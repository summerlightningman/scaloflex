package zadanie2

trait Entity {
  val id: Long
}

case class Avatar(id: Long, name: String, money: Long) extends Entity
