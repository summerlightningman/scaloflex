package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import zadanie1.Stream.End

trait Filter extends Types {

  class FilterActor(method: FilterMethod, mapShuffle: ActorRef) extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("FilterActor has been started!")

    override def receive: Receive = {
      case End =>
        log.info("End has received")
        mapShuffle.forward(End)
      case line: Line =>
        if (method(line)) mapShuffle ! line

    }

    override def unhandled(message: Any): Unit = {
      log.info("Has received a unknown message " + message)
      sender ! "Cannot handle your message: " + message
    }

    override def postStop(): Unit = log.info("FilterActor has been shut down!")
  }

  object FilterActor {
    def props(method: FilterMethod, mapShuffle: ActorRef): Props = Props(new FilterActor(method, mapShuffle))
  }

}

