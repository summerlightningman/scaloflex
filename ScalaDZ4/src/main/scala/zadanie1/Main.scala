package zadanie1

object Main extends App {

}

class Stack(count: Int) {
  private var arr = List[Int]()

  def push(num: Int): Unit = {
    this.arr +:= num
  }

  def pop(): Int = {
    val element = this.arr.last
    this.arr.dropRight(1)
    return element
  }

  def peek(): Int = {
    return this.arr.last
  }
}