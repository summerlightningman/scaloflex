package zadanie1

import java.io.File

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


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
                    doFilter: FilterMethod,
                    doMap: MapMethod,
                    doReduce: ReduceMethod) extends Types {
    val outputPath = s"${System.getProperty("user.dir")}/src/main/resources/output"

    def run(sourceList: Seq[File]): Future[Unit] = {
      Future {
        val system = ActorSystem("cars")

        var counter = 0

        val reducers = for (i <- 1 to countOfReduce) yield system.actorOf(ReduceActor.props(doReduce(i, outputPath)))
        val shuffleActor = system.actorOf(ShuffleActor.props(reducers))
        val mappers = for (_ <- 1 to countOfMap) yield system.actorOf(MapActor.props(doMap(shuffleActor)))
        val filters = for (_ <- 1 to countOfFilter) yield system.actorOf(FilterActor.props(doFilter(mappers)))

        sourceList.foreach(file => {
          filters(counter) ! file
          counter = if (counter == countOfFilter - 1) 0 else counter + 1
        })

      }
    }
  }

}

