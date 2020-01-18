package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import zadanie1.Stream.{End, Query}

trait MapShuffle extends Types {

  class MapShuffleActor(mappers: Seq[ActorRef]) extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("MapShuffleActor has been started!")

    private var counter = 0

    override def receive: Receive = {
      case End =>
        log.info("End has received")
        if (counter == mappers.length) mappers.foreach(_.forward(End))
        else counter += 1
      case Query =>
        mappers.foreach(_.forward(Query))
      case line: Line =>
        mappers(counter) ! line
        counter = if (counter == mappers.length - 1) 0 else (counter + 1)
    }

    override def unhandled(message: Any): Unit = {
      log.info("Has received a unknown message " + message)
      sender ! "Cannot handle your message: " + message
    }

    override def postStop(): Unit = log.info("MapShuffleActor has been shut down!")
  }

  object MapShuffleActor {
    def props(mappers: Seq[ActorRef]): Props = Props(new MapShuffleActor(mappers))

  }

}
