package zadanie2

import scala.concurrent.Future

trait Entity {
  val id: Long
}

case class Avatar(id: Long, name: String, money: Long) extends Entity

trait OperationExecutor[T <: Entity] {
  def executeWithLock(avatars: Seq[T])(operation: T => T): Future[Seq[T]] = ???
}

object Main extends App {
  implicit val executor: OperationExecutor[Avatar] = ???
  executor.executeWithLock(avatars1)(giveMoney)
  executor.executeWithLock(avatars2)(takeMoney)
  executor.executeWithLock(avatars3)(changeName)
}
