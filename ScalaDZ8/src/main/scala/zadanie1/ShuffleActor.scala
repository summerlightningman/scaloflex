package zadanie1

import akka.actor.{Actor, ActorRef, Props}

import scala.collection.parallel.immutable
import scala.collection.{Map, mutable}


class ShuffleActor(reducers: Seq[ActorRef]) extends Actor {

  import ReduceActor._
  import ShuffleActor._

  var keys: Array[(Int, Seq[String])] = (for (i <- reducers.indices) yield (i, Seq[String]())).toArray


  override def preStart(): Unit = {

  }

  override def receive: Receive = {
    case ShuffleData(key, value) =>
      keys.find(_._2.contains(key)) match {
        case Some((i, _)) =>
          reducers(i) ! ReduceData(key, value)
        case None =>
          val minKeys: Int = keys.minBy(_._2.length)._1
          reducers(minKeys) ! ReduceData(key, value)
          keys(minKeys) = (keys(minKeys)._1, keys(minKeys)._2 :+ key)
      }
  }

}

object ShuffleActor {
  def props(reducers: Seq[ActorRef]) = Props(new ShuffleActor(reducers))

  case class ShuffleData(key: String, value: Int)

}
