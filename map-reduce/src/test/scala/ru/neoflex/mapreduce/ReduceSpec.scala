package ru.neoflex.mapreduce

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}
import ru.neoflex.mapreduce.wordcount.{Main, WordCount}

class ReduceSpec extends TestKit(ActorSystem("word-count"))
  with FlatSpecLike
  with BeforeAndAfterAll
  with MustMatchers
  with ImplicitSender {

  "ReduceExecutor" should "receive Unit" in {
    val probe = TestProbe()
    val handler = new ReduceTest

    val outputPath = s"${System.getProperty("user.dir")}/src/main/resources/output/partition-0.txt"
    val reduce: handler.Reduce = (a, b) => a + b
    val reduceExecutor = system.actorOf(handler.ReduceExecutor.props(reduce, outputPath))

    val reducedData = handler.ReduceExecutor.ReduceData("privet", 2)
    reduceExecutor ! reducedData
  }

  private class ReduceTest extends Map with Reader with Master with Shuffle with Reducer with Writer {

    override type ParsedValue = Int
    override type Key = String
    override type MappedValue = Double
  }

}
