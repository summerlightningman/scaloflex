package zadanie1

import java.io.File

import akka.actor.ActorSystem
import akka.pattern._
import akka.util.Timeout

import scala.collection.immutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.io.Source


case object Stream extends Types {
  val system: ActorSystem = ActorSystem("cars")

  def filter[A](countOfFilter: Int)(doFilter: A => Boolean): FilterObject[A] = {
    FilterObject[A](countOfFilter, doFilter)
  }

  case class FilterObject[A](countOfFilter: Int, doFilter: A => Boolean) {
    def map[B](countOfMap: Int)(doMap: A => B): MapObject[A, B] = {
      MapObject(countOfMap, doMap, countOfFilter, doFilter)
    }
  }

  case class MapObject[A, B](countOfMap: Int, map: A => B, countOfFilter: Int, filter: A => Boolean) {
    def reduce(countOfReduce: Int)(reduce: (B, B) => B): Stream[A, B] = {
      Stream(countOfFilter, countOfMap, countOfReduce, filter, map, reduce)
    }
  }

  case class Stream[A, B](countOfFilter: Int,
                    countOfMap: Int,
                    countOfReduce: Int,
                    filter: A => Boolean,
                    map: A => B,
                    reduce: (B, B) => B)
    extends Filter
      with Map
      with Reduce
      with MapShuffle
      with ReduceShuffle
      with Ender {


    def run(sourceList: Seq[A])(system: ActorSystem): Future[Either[String, immutable.Map[Key, B]]] = {

      val reducers = for (i <- 1 to countOfReduce) yield system.actorOf(ReduceActor.props(reduce), s"reduceActor - $i")
      val reduceShuffle = system.actorOf(ReduceShuffleActor.props(reducers), "reduceShuffle")
      val mappers = for (i <- 1 to countOfMap) yield system.actorOf(MapActor.props(map, reduceShuffle), s"mapActor - $i")
      val mapShuffle = system.actorOf(MapShuffleActor.props(mappers))
      val filters = for (i <- 1 to countOfFilter) yield system.actorOf(FilterActor.props(filter, mapShuffle), s"filterActor - $i")
      val ender = system.actorOf(EnderActor.props(countOfReduce))

      sourceList.foreach {
        file: File =>
          val source = Source.fromFile(file)
          source.getLines().foreach(filters(sourceList.indexOf(file) % countOfFilter) ! _)
          source.close()
      }

      implicit val timeout: Timeout = Timeout(30.seconds)
      val result: Future[Either[String, immutable.Map[Key, B]]] = (ender ? Query).map {
        case x: immutable.Map[Key, B] => Right(x)
        case _ => Left("Unknown type")
      }

    }

    case object Query

    override type Line = A
    override type Data = B
  }

}


