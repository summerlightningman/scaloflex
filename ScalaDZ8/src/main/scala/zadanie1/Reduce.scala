package zadanie1

import akka.actor.{Actor, ActorLogging, Props}


trait Reduce extends Types {

  class ReduceActor(method: ReduceMethod) extends Actor with ActorLogging {
    override def preStart(): Unit = log.info("FilterActor has been started!")

    private var reducedData = Seq[Data]()

    override def receive: Receive = {
      case data: Data => reducedData = method(reducedData, data)
    }

    override def postStop(): Unit = log.info("FilterActor has been shut down!")
  }

  object ReduceActor {
    def props(reduce: ReduceMethod) = Props(new ReduceActor(reduce))
  }

}
