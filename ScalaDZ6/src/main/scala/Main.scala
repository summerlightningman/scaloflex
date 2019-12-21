import scala.concurrent._
import java.util.concurrent.ForkJoinPool

object Main extends App {
  val pool = new ForkJoinPool(2)
  val ectx = ExecutionContext.fromExecutorService(pool)
  ectx.execute(new Runnable {
    override def run(): Unit = println("Run on execution context again")
  })

}
