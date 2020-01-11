package zadanie1

import java.io.File

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source


case object Stream extends Types {
  val system = ActorSystem("cars")


  def filter(countOfFilter: Int)(doFilter: FilterMethod): FilterObject = {
    FilterObject(countOfFilter, doFilter)
  }


  case class FilterObject(countOfFilter: Int, doFilter: FilterMethod) {
    def map(countOfMap: Int)(doMap: MapMethod): MapObject = {
      MapObject(countOfMap, doMap, countOfFilter, doFilter)
    }

  }

  case class MapObject(countOfMap: Int,
                       doMap: MapMethod,
                       countOfFilter: Int,
                       doFilter: FilterMethod) {
    def reduce(countOfReduce: Int)(doReduce: ReduceMethod): Stream = {
      Stream(countOfFilter, countOfMap, countOfReduce, doFilter, doMap, doReduce)
    }


  }

  case class Stream(countOfFilter: Int,
                    countOfMap: Int,
                    countOfReduce: Int,
                    filter: FilterMethod,
                    map: MapMethod,
                    reduce: ReduceMethod) extends Filter with Map with Reduce with MapShuffle with ReduceShuffle with Ender {

    val outputPath = s"${System.getProperty("user.dir")}/src/main/resources/output"

    def run(sourceList: Seq[File]): Future[Unit] = {
      val system = ActorSystem("cars")

      val reducers = for (i <- 1 to countOfReduce) yield system.actorOf(ReduceActor.props(reduce), s"reduceActor - $i")
      val reduceShuffle = system.actorOf(ReduceShuffleActor.props(reducers), "reduceShuffle")
      val mappers = for (i <- 1 to countOfMap) yield system.actorOf(MapActor.props(map, reduceShuffle), s"mapActor - $i")
      val mapShuffle = system.actorOf(MapShuffleActor.props(mappers), "mapShuffle")
      val filters = for (i <- 1 to countOfFilter) yield system.actorOf(FilterActor.props(filter, mapShuffle), s"filterActor - $i")
      val ender = system.actorOf(EnderActor.props())

      sourceList.foreach {
        file: File =>
          val source = Source.fromFile(file)
          source.getLines().foreach(filters(sourceList.indexOf(file) % countOfFilter) ! _)
          source.close()
      }

//      ender ? Query

    }

    case object Query

  }

}


