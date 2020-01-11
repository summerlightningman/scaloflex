package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

trait Ender extends Types {

  class EnderActor() extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("FilterActor has been started!")

    private var reducedData = Seq[Data]()

    override def receive: Receive = {
      case data: Seq[Data] => reducedData +: data
      case Query => sender ! reducedData
    }

    override def postStop(): Unit = log.info("FilterActor has been shut down!")
  }

  object EnderActor {
    def props() = Props()
  }

}
