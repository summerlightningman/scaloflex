package zadanie1

import akka.actor.ActorSystem
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


case object Stream extends Types {

  def filter[F](countOfFilter: Int)(doFilter: F => Boolean): FilterObject[F] = {
    FilterObject[F](countOfFilter, doFilter)
  }

  case class FilterObject[F](countOfFilter: Int, doFilter: F => Boolean) {
    def map[M](countOfMap: Int)(doMap: F => Row): MapObject[F] = {
      MapObject[F](countOfMap, doMap, countOfFilter, doFilter)
    }
  }

  case class MapObject[F](countOfMap: Int, map: F => Row, countOfFilter: Int, filter: F => Boolean) {
    def reduce(countOfReduce: Int)(reduce: (Row, Row) => Row): Stream[F] = {
      Stream[F](countOfFilter, countOfMap, countOfReduce, filter, map, reduce)
    }

  }

  case class ReduceData(key: Key, value: Int)

  case class Stream[F](countOfFilter: Int,
                       countOfMap: Int,
                       countOfReduce: Int,
                       filter: F => Boolean,
                       map: F => Row,
                       reduce: (Row, Row) => Row)
    extends Filter
      with Mapper
      with Reducer
      with MapShuffle
      with ReduceShuffle
      with EndShuffle
      with Master {

    override type Line = F
    //    override type Data = M

    def run(sourceList: Seq[F], system: ActorSystem): Future[Map[Key, Int]] = {
      val master = system.actorOf(MasterActor.props(countOfFilter, countOfMap, countOfReduce, filter, map, reduce), "master")
      implicit val timeout: Timeout = Timeout(30.seconds)
      master ! sourceList
      (master ? Query).map {
        case data: Map[Key, Int] => data
      }
    }

  }

  case object Query

  case object End


}


