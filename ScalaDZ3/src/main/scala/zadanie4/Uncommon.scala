package zadanie4

case class Uncommon(weight: Double) extends Package(weight) {
  override def pack(): Double = (weight * 1.2) + math.floor(weight / 5) * 1.1
}
