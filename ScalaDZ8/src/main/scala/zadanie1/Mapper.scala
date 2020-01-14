package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import zadanie1.Stream.End

trait Mapper extends Types {

  class MapActor(method: MapMethod, reduceShuffle: ActorRef) extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("MapActor has been started!")

    override def receive: Receive = {
      case End =>
        log.info("End has received")
        reduceShuffle.forward(End)
      case line: Line =>
        reduceShuffle ! method(line)
    }

    override def unhandled(message: Any): Unit = {
      log.info("Has received a unknown message " + message)
      sender ! "Cannot handle your message: " + message
    }

    override def postStop(): Unit = log.info("MapActor has been shut down!")

  }

  object MapActor {
    def props(method: MapMethod, reduceShuffle: ActorRef): Props = Props(new MapActor(method, reduceShuffle))
  }

}

