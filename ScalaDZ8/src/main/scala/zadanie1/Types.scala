package zadanie1

import java.io.File

import akka.actor.ActorRef

trait Types {
  type Line = String

  type FilterMethod = Line => Boolean
  type MapMethod = Line => Data
  type ReduceMethod = (Seq[Data], Data) => Seq[Data]

  type ActorFilter = (File) => Unit


  type Key = String

  case class Data(key: Key, value: Int)

}