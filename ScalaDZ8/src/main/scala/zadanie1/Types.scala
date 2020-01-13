package zadanie1


trait Types {

  type Line
  type Data

  type FilterMethod = Line => Boolean
  type MapMethod = Line => Data
  type ReduceMethod = (Data, Data) => Data]

  type Key = String

  case object Query

}
