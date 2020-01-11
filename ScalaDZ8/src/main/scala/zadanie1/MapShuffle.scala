package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

trait MapShuffle extends Types {

  class MapShuffleActor(mappers: Seq[ActorRef]) extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("MapShuffleActor has been started!")

    override def receive: Receive = {
      case lines: Seq[Line] =>
        lines.foreach {
          line: Line => mappers(lines.indexOf(line) % mappers.length) ! line
        }
    }

    override def postStop(): Unit = log.info("MapShuffleActor has been shut down!")
  }

  object MapShuffleActor {
    def props(mappers: Seq[ActorRef]): Unit = Props(new MapShuffleActor(mappers))

  }

}
