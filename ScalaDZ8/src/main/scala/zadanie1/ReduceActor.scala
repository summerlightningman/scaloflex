package zadanie1

import java.io.PrintWriter

import akka.actor.{Actor, Props}


class ReduceActor(reduce: Array[(String, Int)] => Unit) extends Actor {

  import ReduceActor._

  private var reducedData = Array[(String, Int)]()

  override def receive: Receive = {
    case ReduceData(key, value) =>
      reducedData = (reducedData :+ (key, value)).groupBy(_._1).map {
        case (key, values) => (key, values.map(_._2).toList.sum)
      }.toArray
    //      val writer = new PrintWriter(s"${outputPath}/cars-${index}.csv")
    //      reducedData.foreach {
    //        case (key, value) => writer.write(s"${key},${value}\n")
    //      }
    //      writer.close()
      reduce(reducedData)
  }
}

object ReduceActor {
  def props(reduce: Array[(String, Int)] => Unit) = Props(new ReduceActor(reduce))

  case class ReduceData(key: String, value: Int)

}