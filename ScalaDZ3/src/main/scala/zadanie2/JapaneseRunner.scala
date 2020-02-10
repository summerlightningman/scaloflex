package zadanie2

case class JapaneseRunner(override val name: String) extends Runner {
  override def run(x: Double): Double = math.pow(math.E, x)
}
