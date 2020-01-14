package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import zadanie1.Stream.{End, Key, ReduceData}

trait ReduceShuffle extends Types {

  class ReduceShuffleActor(reducers: Seq[ActorRef]) extends Actor with ActorLogging {

    import ReduceShuffleActor._

    var keys: Array[(Int, Seq[Key])] = reducers.indices.map(i => (i, Seq[Key]())).toArray
    var j = 0

    override def preStart(): Unit = log.info("ReduceShuffleActor has been started!")

    override def receive: Receive = {
      case End =>
        log.info("End has received")
        if (j == reducers.length) reducers.foreach(_ ! End)
        else j += 1
      case (key: Key, value: Int) =>
        keys.find(_._2.contains(key)) match {
          case Some((i, _)) =>
            reducers(i) ! ReduceData(key, value)
          case None =>
            val minKeys: Int = keys.minBy(_._2.length)._1
            reducers(minKeys) ! ReduceData(key, value)
            keys(minKeys) = (keys(minKeys)._1, keys(minKeys)._2 :+ key)
        }

    }

    override def unhandled(message: Any): Unit = {
      log.info("Has received a unknown message " + message)
      sender ! "Cannot handle your message: " + message
    }

    override def postStop(): Unit = log.info("ReduceShuffleActor has been shut down!")

  }

  object ReduceShuffleActor {
    def props(reducers: Seq[ActorRef]): Props = Props(new ReduceShuffleActor(reducers))

  }

}


