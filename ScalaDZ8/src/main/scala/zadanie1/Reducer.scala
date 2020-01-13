package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import zadanie1.Stream.End


trait Reducer extends Types {

  class ReduceActor(method: ReduceMethod, endShuffle: ActorRef) extends Actor with ActorLogging {
    override def preStart(): Unit = log.info("FilterActor has been started!")

    private var reducedData = Seq[Data]()

    override def receive: Receive = {
      case data: Data =>
        // yes
        reducedData = method(reducedData, data)

      case End =>
        println("End has received")
        endShuffle ! reducedData
        endShuffle.forward(End)
    }

    override def unhandled(message: Any): Unit = {
      log.info("Has received a unknown message " + message)
      sender ! "Cannot handle your message: " + message
    }

    override def postStop(): Unit = log.info("FilterActor has been shut down!")
  }

  object ReduceActor {
    def props(reduce: ReduceMethod, endShuffle: ActorRef): Props = Props(new ReduceActor(reduce, endShuffle))
  }

}
