package zadanie2

import java.util.concurrent.{ForkJoinPool, ForkJoinTask, RecursiveTask}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

trait Entity {
  val id: Long
}

case class Avatar(id: Long, name: String, money: Long) extends Entity

trait OperationExecutor[T <: Entity] {
  def executeWithLock(avatars: Seq[T])(operation: T => T): Future[Seq[T]]

  def giveMoney(avatar: T): T

  def takeMoney(avatar: T): T

  def changeName(avatar: T): T
}

class NewYear extends OperationExecutor[Avatar] {
  def executeWithLock(avatars: Seq[Avatar])(operation: Avatar => Avatar): Future[Seq[Avatar]] = Future {
    avatars.synchronized {
      avatars.map(operation)
    }
  }

  override def giveMoney(avatar: Avatar): Avatar = Avatar(avatar.id, avatar.name, avatar.money + 2020)

  override def takeMoney(avatar: Avatar): Avatar = Avatar(avatar.id, avatar.name, avatar.money - 2020)

  override def changeName(avatar: Avatar): Avatar = Avatar(avatar.id, "old_" + avatar.name, avatar.money)
}

// Второй вариант с ForkJoin'ом не удалось сделать :(
// Даже не имею представления как сделать
//class VictoryDay extends OperationExecutor[Avatar] {
//  override def executeWithLock(avatars: Seq[Avatar])(operation: Avatar => Avatar): Future[Seq[Avatar]] = Future {
//    implicit val executor: OperationExecutor[Avatar] = this
//    val threads: Int = Runtime.getRuntime.availableProcessors()
//    val processor = new ForkJoinPool(threads)
//    val tasks = avatars.map {
//      avatar: Avatar => new RecursiveTask[Avatar] {
//        override def compute(): Avatar = {
//
//        }
//      }
//    }.toList
//    processor.execute(tasks.take(0))
//  }
//
//
//  override def giveMoney(avatar: Avatar): Avatar = Avatar(avatar.id, avatar.name, avatar.money + 2020)
//
//  override def takeMoney(avatar: Avatar): Avatar = Avatar(avatar.id, avatar.name, avatar.money - 2020)
//
//  override def changeName(avatar: Avatar): Avatar = Avatar(avatar.id, "9ofMay_" + avatar.name, avatar.money)
//}

object Main {
  def main(args: Array[String]): Unit = {
    implicit val executor: OperationExecutor[Avatar] = new NewYear
    val avatars: Seq[Avatar] = Seq(
      Avatar(1, "Dima", 4350345034L),
      Avatar(2, "Danil", 202020202020L),
      Avatar(3, "Denis", 2018201920202021L),
      Avatar(4, "Boris", 1234567891011124L)
    )

    val result1 = executor.executeWithLock(avatars)(executor.giveMoney)
    val result2 = executor.executeWithLock(avatars)(executor.takeMoney)
    val result3 = executor.executeWithLock(avatars)(executor.changeName)
    Thread.sleep(2000)
    println(result1)
    println(result2)
    println(result3)
  }

}
