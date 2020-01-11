package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

trait Map extends Types {

  class MapActor(method: MapMethod, reduceShuffle: ActorRef) extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("MapActor has been started!")

    override def receive: Receive = {
      case line: Line =>
        reduceShuffle ! method(line)
    }

    override def postStop(): Unit = log.info("MapActor has been shut down!")

  }

  object MapActor {
    def props(method: MapMethod, reduceShuffle: ActorRef) = Props(new MapActor(method, reduceShuffle))
  }

}

