package zadanie1

import akka.actor.{Actor, ActorLogging, Props}
import zadanie1.Main.Data


trait Reduce extends Types {

  class ReduceActor(method: ReduceMethod) extends Actor with ActorLogging {
    override def preStart(): Unit = log.info("FilterActor has been started!")

    private var reducedData = Map[Key, Data]()

    override def receive: Receive = {
      case data: Data => reducedData = method(reducedData, data)
      case End =>
    }

    override def postStop(): Unit = log.info("FilterActor has been shut down!")
  }

  object ReduceActor {
    def props(reduce: ReduceMethod): Props = Props(new ReduceActor(reduce))
  }

}
