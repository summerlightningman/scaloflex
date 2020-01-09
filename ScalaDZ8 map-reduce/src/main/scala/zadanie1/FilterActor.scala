package zadanie1

import java.io.File

import akka.actor.{Actor, ActorLogging, Props}


class FilterActor(filter: (File) => Unit) extends Actor with ActorLogging {

  override def receive: Receive = {
    case file: File => filter(file)
  }


}

object FilterActor {
  def props(filter: File => Unit) = Props(new FilterActor(filter))

  case object End

}