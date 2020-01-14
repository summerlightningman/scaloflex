package zadanie1

import akka.actor.{Actor, ActorLogging, Props}
import zadanie1.Stream.ReduceData
import zadanie1.Stream.End

import scala.collection.mutable

trait Reducer extends Types {

  class ReduceActor(method: ReduceMethod) extends Actor with ActorLogging {
    override def preStart(): Unit = log.info("FilterActor has been started!")

    private val reducedData = mutable.Map.empty[Key, Int]

    override def receive: Receive = {
      case End =>
        log.info("End has received")
        context.parent ! reducedData
      case ReduceData(key, value) =>
        val reducedValue = reducedData
          .get(key)
          .map(x => method((key, x), (key, value)))
          .getOrElse(value)
        reducedData.update(key, reducedValue)

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
