package zadanie1

import akka.actor.{Actor, ActorRef, Props}
import zadanie1.Stream.{End, Query}

trait Master extends Types with Filter
  with Mapper
  with Reducer
  with MapShuffle
  with ReduceShuffle {

  class MasterActor(countOfFilter: Int,
                    countOfMap: Int,
                    countOfReduce: Int,
                    filter: FilterMethod,
                    map: MapMethod,
                    reduce: ReduceMethod
                   ) extends Actor {

    var reducedData: Map[Key, Int] = Map.empty[Key, Int]

    val reducers: Seq[ActorRef] = (0 to countOfReduce).map(i => context.actorOf(ReduceActor.props(reduce), s"reduceActor-$i"))
    val reduceShuffle: ActorRef = context.actorOf(ReduceShuffleActor.props(reducers), "reduceShuffle")
    val mappers: Seq[ActorRef] = (0 to countOfMap).map(i => context.actorOf(MapActor.props(map, reduceShuffle), s"mapActor-$i"))
    val mapShuffle: ActorRef = context.actorOf(MapShuffleActor.props(mappers), "mapShuffle")
    val filters: Seq[ActorRef] = (0 to countOfFilter).map(i => context.actorOf(FilterActor.props(filter, mapShuffle), s"filterActor-$i"))

    override def receive: Receive = {
      case sourceList: Seq[Line] =>
        sourceList.foreach(line => filters(sourceList.indexOf(line) % filters.length) ! line)
        filters.foreach(_ ! End)
        sender ! End
      case data: Map[Key, Int] => reducedData ++= data
      case Query =>
        sender ! reducedData
    }
  }

  object MasterActor {
    def props(countOfFilter: Int,
              countOfMap: Int,
              countOfReduce: Int,
              filter: FilterMethod,
              map: MapMethod,
              reduce: ReduceMethod): Props = Props(
      new MasterActor(countOfFilter, countOfMap, countOfReduce, filter, map, reduce)
    )
  }

}
