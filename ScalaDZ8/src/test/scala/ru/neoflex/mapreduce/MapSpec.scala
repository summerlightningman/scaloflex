package ru.neoflex.mapreduce

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}

class MapSpec extends TestKit(ActorSystem("word-count")) with FlatSpecLike with BeforeAndAfterAll with MustMatchers with ImplicitSender {

  import ShuffleExecutor._

  "Shuffle" should "receive \"privet\"" in {
    val sender = TestProbe()
    val shuffle = system.actorOf(Props[Shuffle])
    sender.send(shuffle, "privet")


  }

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }
}
