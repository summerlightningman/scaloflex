package zadanie2

import scala.concurrent.Future

trait OperationExecutor[T <: Entity] {
  def executeWithLock(avatars: Seq[T])(operation: T => T): Future[Seq[T]]
}
