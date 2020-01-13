package zadanie1

import akka.actor.ActorSystem
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent._
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
      with ReduceShuffle
      with EndShuffle
      with Master {

    override type Line = F
    override type Data = M

    def run(sourceList: Seq[F], system: ActorSystem): Future[Map[Key, Int]] = {


      val endShuffle = system.actorOf(EndShuffleActor.props(countOfReduce), "endShuffle")
      val reducers = (0 to countOfReduce).map(i => system.actorOf(ReduceActor.props(reduce, endShuffle), s"reduceActor-$i"))
      val reduceShuffle = system.actorOf(ReduceShuffleActor.props(reducers), "reduceShuffle")
      val mappers = (0 to countOfMap).map(i => system.actorOf(MapActor.props(map, reduceShuffle), s"mapActor-$i"))
      val mapShuffle = system.actorOf(MapShuffleActor.props(mappers), "mapShuffle")
      val filters = (0 to countOfFilter).map(i => system.actorOf(FilterActor.props(filter, mapShuffle), s"filterActor-$i"))
      val master = system.actorOf(MasterActor.props(filters))

      implicit val timeout: Timeout = Timeout(30.seconds)

      sourceList.foreach(line => filters(sourceList.indexOf(line) % filters.length) ! line)
      filters.foreach(_ ! End)
      (master ? Query).mapTo[Map[Key, Int]]

    }

  }

  case object Query

  case object End
}


