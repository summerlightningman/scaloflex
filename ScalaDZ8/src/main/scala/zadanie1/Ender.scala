package zadanie1

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import zadanie1.Main.Data

import scala.collection.mutable

trait Ender extends Types {

  class EnderActor(countOfReduce: Int) extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("EnderActor has been started!")

    private val reducedData = mutable.Seq[Map]()
    private var reduces = 0

    override def receive: Receive = {
      case data: Seq[Data] =>
        reducedData +: data.map(data => Map(data.key -> data.value))
        reduces += 1
        log.info(s"$sender has sent data")
      case Query =>
        while (reduces != countOfReduce) {}
        log.info("Sending data to stream")
        sender ! reducedData
    }

    override def postStop(): Unit = log.info("EnderActor has been shut down!")
  }

  object EnderActor {
    def props(countOfReduce: Int): Props = Props(new EnderActor(countOfReduce))
  }

}
