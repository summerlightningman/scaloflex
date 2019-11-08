package zadanie4

case class Common(weight: Double) extends Package(weight) {
  override def pack(): Double = weight * 1.1
}
