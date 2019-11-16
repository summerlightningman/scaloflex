package zadanie2

object Main extends App {

  implicit class Prettify(i: Int) {
    implicit def prettyInt: String = {
      "--" + "-" * Integer.valueOf(i).toString.length + "--\n" +
        "| " + i.toString + " |\n" +
        "--" + "-" * Integer.valueOf(i).toString.length + "--"
    }
  }

  println(28091999.prettyInt)
}
