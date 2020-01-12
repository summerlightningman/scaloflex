package zadanie1

import zadanie1.Main.Data

trait Types {
  type Line = String

  type FilterMethod = Line => Boolean
  type MapMethod = Line => Data
  type ReduceMethod = (Seq[Data], Data) => Seq[Data]

  type Key = String

  case object Query

}
