package zadanie1

object Main extends App {


}

case class MultiList(size: Int) {
  val head: Array[Int] = new Array[Int](size)
  val next: Array[Int] = new Array[Int](size + 1)
  val data: Array[Int] = new Array[Int](size + 1)
  var count = 1

  def add(h: Int, v: Int): Unit = {
    next(count) = head(h)
    data(count) = v
    count += 1
    head(h) = count
  }
}