package zadanie1

import akka.actor.{Actor, ActorRef, Props}
import zadanie1.Stream.{End, Query}

trait Master extends Types {

  class MasterActor(filters: Seq[ActorRef]) extends Actor {

    var reducedData: Seq[Data] = Seq[Data]()

    override def receive: Receive = {
      case Query =>
        filters.foreach(_ ! End)
      case End =>

    }
  }

  object MasterActor {
    def props(filters: Seq[ActorRef]): Props = Props(new MasterActor(filters))
  }

}
