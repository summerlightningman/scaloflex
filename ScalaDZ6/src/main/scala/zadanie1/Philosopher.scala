package zadanie1


trait Philosopher {
  def takeLeft(withLeft: => Unit): Unit

  def takeRight(withRight: => Unit): Unit

  val name: String

  val left: Int
  val right: Int

  final def live(): Unit = {
    takeLeft {
      takeRight {
        eat()
      }
    }
  }

  def eat(): Unit = println(s"$name is eating")
}
