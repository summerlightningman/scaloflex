package zadanie1

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import zadanie1.Main.Car


class MapActor(map: (String, String, String) => Unit) extends Actor {

  import ShuffleActor._

  val log = Logging(context.system, this)

  override def receive: Receive = {
    case Car(brand, model, year) =>
      map(brand, model, year)
    //      val car = s"${brand} ${model} (${year})"
    //      shuffle ! ShuffleData(car, 1)
  }


}

object MapActor {
  def props(map: (String, String, String) => Unit) = Props(new MapActor(map))
}