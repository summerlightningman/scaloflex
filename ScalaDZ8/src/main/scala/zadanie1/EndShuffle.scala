package zadanie1

import akka.actor.{Actor, ActorLogging, Props}
import zadanie1.Stream.End

trait EndShuffle extends Types {


  class EndShuffleActor(countOfReduce: Int) extends Actor with ActorLogging {
    var j = 0

    private var reducedData = Map[Key, Int]()

    override def preStart(): Unit = log.info("MapShuffleActor has been started!")

    override def receive: Receive = {
      case data: Seq[Data] =>
        reducedData = (data.toList ++ reducedData.toList).groupBy {
          case (key: Key, _) => key
        }.map {
          case (key, value) => key -> value.length
        }

      case End =>
        j += 1
        if (j == countOfReduce) sender ! reducedData

    }

    override def postStop(): Unit = log.info("MapShuffleActor has been shut down!")
  }

  object EndShuffleActor {
    def props(countOfReduce: Int): Props = {
      Props(new EndShuffleActor(countOfReduce))
    }
  }

}
