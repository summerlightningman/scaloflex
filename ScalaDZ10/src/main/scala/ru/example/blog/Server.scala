package ru.example.blog

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import ru.example.blog.serialize.JsonFormats.ConfigSerializer
import scala.io.Source

import spray.json._

object Server extends App {


  implicit val actorSystem = ActorSystem("blog")
  implicit val materializer = ActorMaterializer()
  implicit val ec = actorSystem.dispatcher
  val logger = actorSystem.log

  val wsRouter: ActorRef = actorSystem.actorOf(WsRouter.props)

  val routes = new Controller(wsRouter).build

  val configPath = s"${System.getProperty("user.dir")}/src/main/resources/application.conf"
  val configFile = Source.fromFile(configPath)
  val config = configFile.mkString.parseJson.convertTo[Config]
  configFile.close()

  Http()
    .bindAndHandle(routes, config.host, config.port)
    .map(_ => logger.info(s"server start ${config.host}:${config.port}"))


}
