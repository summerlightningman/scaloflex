import scala.io.StdIn._

object Main extends {

  def main(args: Array[String]): Unit = { // Основной метод класса, консольный интерфейс
    println("Hello! What exercise want you do?\n1 - partApply\n2 - compose\n3 - curry")

    readInt() match {
      case 1 =>
        val partial = partApply(_ + _, 2)
        val result = partial(0)
        println(result)
      case 2 =>
        fg(10)
      case 3 =>
        val procedure = readLine("What operation want you do?\n1 - Middle arithmetic\n2 - Absolute reduce") match {
          case "1" => curry(middle)
          case "2" => curry(absReduce)
        }
        println("Input numbers with space separator")
        val nums = readLine().split(" ")
        print(procedure(nums(0).toInt)(nums(1).toDouble))

    }
  }

  def partApply(f: (Int, Int) => Int, x: Int): Int => Int = {
    f(_, x)
  }

  def curry(f: (Int, Double) => String): Int => Double => String = (b: Int) => f(b, _)

  def middle(v1: Int, v2: Double): String = {
    val result: Double = (v1 + v2) / 2
    f"Middle number between $v1 and $v2 = $result"
  }

  def absReduce(v1: Int, v2: Double): String = {
    val result: Double = math.abs(v1 - v2)
    f"Absolute reduce of $v1 and $v2 = $result"
  }

  def f(x: Int): Double = x.toDouble

  def g(y: Double): String = y.toString


  val fg: Int => String = f _ andThen g


}
