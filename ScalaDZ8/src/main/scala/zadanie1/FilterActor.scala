package zadanie1

import java.io.File

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import zadanie1.FilterActor.End
import zadanie1.Main.Car


class FilterActor(filter: (File) => Unit) extends Actor with ActorLogging {

  override def receive: Receive = {
    case file: File =>
      //      val source = io.Source.fromFile(file)
      //      var i = 0
      //      for (line <- source.getLines) {
      //        val cols = line.split(",").map(_.trim).toSeq
      //        try
      //          mappers(i) ! Car(cols(2), cols(3), cols(4))
      //        catch {
      //          case _: Exception =>
      //        }
      //        i = if (i == mappers.length) 0 else i + 1
      //      }
      //      source.close
      filter(file)
  }


}

object FilterActor {
  def props(filter: File => Unit) = Props(new FilterActor(filter))

  case object End

}