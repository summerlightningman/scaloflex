package zadanie1

import akka.actor.ActorSystem
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._


case object Stream {

  def filter[F](countOfFilter: Int)(doFilter: F => Boolean): FilterObject[F] = {
    FilterObject[F](countOfFilter, doFilter)
  }

  case class FilterObject[F](countOfFilter: Int, doFilter: F => Boolean) {
    def map[M](countOfMap: Int)(doMap: F => M): MapObject[F, M] = {
      MapObject[F, M](countOfMap, doMap, countOfFilter, doFilter)
    }
  }

  case class MapObject[F, M](countOfMap: Int, map: F => M, countOfFilter: Int, filter: F => Boolean) {
    def reduce(countOfReduce: Int)(reduce: (Seq[M], M) => Seq[M]): Stream[F, M] = {
      Stream[F, M](countOfFilter, countOfMap, countOfReduce, filter, map, reduce)
    }
  }

  case class Stream[F, M](countOfFilter: Int,
                          countOfMap: Int,
                          countOfReduce: Int,
                          filter: F => Boolean,
                          map: F => M,
                          reduce: (Seq[M], M) => Seq[M])
    extends Filter
      with Mapper
      with Reducer
      with MapShuffle
      with ReduceShuffle {

    override type Line = F
    override type Data = M

    def run(sourceList: Seq[F], system: ActorSystem): Unit = {
      val reducers = (0 to countOfReduce).map(i => system.actorOf(ReduceActor.props(reduce), s"reduceActor-$i"))
      val reduceShuffle = system.actorOf(ReduceShuffleActor.props(reducers), "reduceShuffle")
      val mappers = (0 to countOfMap).map(i => system.actorOf(MapActor.props(map, reduceShuffle), s"mapActor-$i"))
      val mapShuffle = system.actorOf(MapShuffleActor.props(mappers), "mapShuffle")
      val filters = (0 to countOfFilter).map(i => system.actorOf(FilterActor.props(filter, mapShuffle), s"filterActor-$i"))


      //      implicit val timeout: Timeout = Timeout(30.seconds)

      sourceList.foreach(line => filters(sourceList.indexOf(line) % filters.length) ! line)
      filters.foreach(_ ! End)

      //      (filters(0) ? Query).map {
      //        case x: Map[Key, M] => Right(x)
      //        case _ => Left("Unknown type")
      //      }
    }
  }

  case object Query

  case object End

}


