package ru.neoflex.mapreduce

import java.io.File
import java.nio.charset.UnmappableCharacterException

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

import scala.io.{BufferedSource, Source}

trait Reader {

  data: Map with Master =>

  class DataReader(parse: Parse, initPartition: File, map: Map, shuffle: ActorRef) extends Actor with ActorLogging {

    import DataReader._
    import MapExecutor._
    import MasterExecutor._

    private val mapExecutor = context.actorOf(MapExecutor.props(map, shuffle))

    readFileAndSendData(initPartition)

    override def receive: Receive = {
      case NextPartition(file) =>
        readFileAndSendData(file)
      case LastDone => context.parent ! Free(self)
      case End => mapExecutor.forward(End)
    }

    private def readFileAndSendData(file: File): Unit = {
      log.info(s"Start reading ${file.getCanonicalPath}")
      val source = Source.fromFile(file)
      try {
        source.getLines().foreach(x => mapExecutor ! Data(parse(x)))
      } catch {
        case e: UnmappableCharacterException => log.info("Input length = 1")
      }
      mapExecutor ! Last
      source.close()
    }

    override def preStart(): Unit = {
      log.info("DataReader is starting")
    }

    override def postStop(): Unit = {
      log.info("DataReader is stopped")
    }

  }

  case object DataReader {

    def props(parse: Parse, initPath: File, map: Map, shuffle: ActorRef): Props = {
      Props(new DataReader(parse, initPath, map, shuffle))
    }

    case class NextPartition(path: File)

    case class Free(dataReader: ActorRef)

    case object Last

  }

}

