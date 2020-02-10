package zadanie1

object Main extends App {


}

case class MyList[Contain](elems: Contain*) {
  def separateBy(count: Int): IndexedSeq[Seq[Contain]] =
    for (i <- 0 to elems.size if i % count == 0) yield Seq(elems(i + 1), elems(i + 2), elems(i + 3))

  def ::(left: Int, right: Int): Seq[Contain] = {
    if (left > right)
      throw new Exception("Left argument bigger than right")
    else {
      for (i <- left to right) yield elems(i)
    }
  }

  override def toString: String = {
    s"[${elems.mkString(" ")}]"
  }
}