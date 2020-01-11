package ru.neoflex.mapreduce

import java.io.File

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}
import ru.neoflex.mapreduce.wordcount.WordCount


class ReaderSpec extends TestKit(ActorSystem("word-count"))
  with FlatSpecLike
  with BeforeAndAfterAll
  with MustMatchers
  with ImplicitSender {

  val initPartition = new File(s"${System.getProperty("user.dir")}/src/main/resources/output/partition-0.txt")

  "MasterExecutor" should "receive Free with Reader's ActorRef" in {
    val master = TestProbe()
    val shuffle = TestProbe()
    val handler = new ReaderTest

    val reader = master.childActorOf(handler.DataReader.props(identity, initPartition, map, shuffle.ref))

    reader ! LastDone
    master.expectMsg(handler.DataReader.Free(reader))
  }

  case object LastDone

  case class Free(dataReader: ActorRef)

  def map(str: String): Seq[(String, WordCount)] = {
    val punctuationMarks = Seq(".", ",", "!", "?", "(", ")", "-", "“", "”", ":", ";")
    str
      .split(" ")
      .toList
      .map { word =>
        val pureWord = punctuationMarks.foldLeft(word) { case (w, mark) => w.replace(mark, "") }
        pureWord -> WordCount(pureWord, 1)
      }
  }

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  private class ReaderTest extends Map with Reader with Master with Shuffle with Reducer with Writer {

    override type ParsedValue = String
    override type Key = String
    override type MappedValue = WordCount
  }

}
