package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

trait ReduceShuffle extends Types {

  class ReduceShuffleActor(reducers: Seq[ActorRef]) extends Actor with ActorLogging {

    var keys: Array[(Int, Seq[Key])] = (for (i <- reducers.indices) yield (i, Seq[Key]())).toArray

    override def preStart(): Unit = log.info("ReduceShuffleActor has been started!")

    override def receive: Receive = {
      case Data(key, value) =>
        keys.find(_._2.contains(key)) match {
          case Some((i, _)) =>
            reducers(i) ! Data(key, value)
          case None =>
            val minKeys: Int = keys.minBy(_._2.length)._1
            reducers(minKeys) ! Data(key, value)
            keys(minKeys) = (keys(minKeys)._1, keys(minKeys)._2 :+ key)
        }
    }

    override def postStop(): Unit = log.info("ReduceShuffleActor has been shut down!")
  }

  object ReduceShuffleActor {
    def props(reducers: Seq[ActorRef]) = Props(new ReduceShuffleActor(reducers))

  }

}


