package zadanie2

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

class AvatarExecutor[T] extends OperationExecutor[T] {
  override def executeWithLock(avatars: Seq[T])(operation: T => T): Future[Seq[T]] =
    Future(avatars.map(execute(_, operation)))


  def execute(avatar: T, body: T => T): T = ExecutionContext.global.execute {
    new Runnable {
      override def run(): T = body(avatar)
    }
  }


}
