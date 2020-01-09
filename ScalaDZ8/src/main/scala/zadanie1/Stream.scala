package zadanie1

import java.io.File

import akka.actor.{ActorRef, ActorSystem}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


case object Stream {
  val system = ActorSystem("cars")

  def filter(countOfFilter: Int)(doFilter: Seq[ActorRef] => File => Unit): FilterObject = {
    FilterObject(countOfFilter, doFilter)
  }


  case class FilterObject(countOfFilter: Int, doFilter: Seq[ActorRef] => File => Unit) {
    def map(countOfMap: Int)(doMap: ActorRef => (String, String, String) => Unit): MapObject = {
      MapObject(countOfMap, doMap, countOfFilter, doFilter)
    }

  }

  case class MapObject(
                        countOfMap: Int,
                        doMap: ActorRef => (String, String, String) => Unit,
                        countOfFilter: Int,
                        doFilter: Seq[ActorRef] => File => Unit) {
    def reduce(countOfReduce: Int)(doReduce: (Int, String) => Array[(String, Int)] => Unit): Stream = {
      new Stream(countOfFilter, countOfMap, countOfReduce, doFilter, doMap, doReduce)
    }
  }

}

case class Stream(countOfFilter: Int,
                  countOfMap: Int,
                  countOfReduce: Int,
                  doFilter: Seq[ActorRef] => File => Unit,
                  doMap: ActorRef => (String, String, String) => Unit,
                  doReduce: (Int, String) => Array[(String, Int)] => Unit) {
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