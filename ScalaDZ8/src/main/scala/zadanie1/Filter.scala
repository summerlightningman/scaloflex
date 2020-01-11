package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

trait Filter extends Types {

  class FilterActor(method: FilterMethod, mapShuffle: ActorRef) extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("FilterActor has been started!")

    override def receive: Receive = {
      case lines: Seq[Line] =>
        val rows: Seq[Line] = lines.filter(method)
        rows.foreach(mapShuffle ! _)
    }

    override def postStop(): Unit = log.info("FilterActor has been shut down!")
  }

  object FilterActor {
    def props(method: FilterMethod, map: ActorRef) = Props(new FilterActor(method, map))

    case object End

  }

}

