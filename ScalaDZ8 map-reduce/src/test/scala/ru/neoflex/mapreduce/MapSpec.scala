package ru.neoflex.mapreduce

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}

class MapSpec
  extends TestKit(ActorSystem("word-count"))
    with FlatSpecLike
    with BeforeAndAfterAll
    with MustMatchers
    with ImplicitSender {


  val map: Int => Seq[(String, Double)] = x => Seq("key" -> x / 15d)
  val shuffle = TestProbe()

  "MapExecutor" should "apply map function" in {
    val handler = new MapTest
    val key = "key"
    val mapExecutor = system.actorOf(handler.MapExecutor.props(map, shuffle.ref))

    val data = 10
    mapExecutor ! handler.MapExecutor.Data(data)
    val expectMessage = handler.ShuffleExecutor.ShuffleData(key, data / 15d)
    shuffle.expectMsg(expectMessage)
  }

  "MapExecutor" should "receive END" in {
    val handler = new MapTest
    val mapExecutor = system.actorOf(handler.MapExecutor.props(map, shuffle.ref))
    mapExecutor ! handler.MasterExecutor.End
    shuffle.expectMsg(handler.MasterExecutor.End)
  }

  "Reader" should "receive LastDone" in {
    val handler = new MapTest
    val reader = TestProbe()
    val mapExecutor = reader.childActorOf(handler.MapExecutor.props(map, TestProbe().ref))
    mapExecutor ! handler.DataReader.Last
    reader.expectMsg(handler.MapExecutor.LastDone)
  }

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  private class MapTest extends Map with Reader with Master with Shuffle with Reducer with Writer {

    override type ParsedValue = Int
    override type Key = String
    override type MappedValue = Double

  }

}
