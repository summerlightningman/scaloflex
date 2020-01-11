package zadanie1

import akka.actor.{Actor, Props}


trait Reduce extends Types {

  class ReduceActor(method: ReduceMethod) extends Actor {

    private var reducedData = Seq[Data]()

    override def receive: Receive = {
      case data: Data => reducedData = method(reducedData, data)
    }
  }

  object ReduceActor {
    def props(reduce: ReduceMethod) = Props(new ReduceActor(reduce))
  }

}
