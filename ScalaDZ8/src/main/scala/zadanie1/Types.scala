package zadanie1

trait Types {
  type Line
//  type Data
  type Key = String

  type FilterMethod = Line => Boolean
  type MapMethod = Line => Row
  type ReduceMethod = (Row, Row) => Row
  type Row = (Key, Int)
}
