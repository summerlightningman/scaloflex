package zadanie1

import akka.actor.{Actor, ActorLogging, Props}
import zadanie1.Stream.End


trait Reducer extends Types {

  class ReduceActor(method: ReduceMethod) extends Actor with ActorLogging {
    override def preStart(): Unit = log.info("FilterActor has been started!")

    private var reducedData = Seq[Data]()

    override def receive: Receive = {
      case data: Data =>
        reducedData = method(reducedData, data)
      case End => log.info(sender.toString())
    }

    override def unhandled(message: Any): Unit = {
      log.info("Has received a unknown message " + message)
      sender ! "Cannot handle your message: " + message
    }

    override def postStop(): Unit = log.info("FilterActor has been shut down!")
  }

  object ReduceActor {
    def props(reduce: ReduceMethod): Props = Props(new ReduceActor(reduce))
  }

}
