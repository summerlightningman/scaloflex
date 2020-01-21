package ru.example.blog

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config._
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global

object Server extends App {

  implicit val actorSystem = ActorSystem("blog")
  implicit val materializer = ActorMaterializer()
  implicit val ec = actorSystem.dispatcher
  val logger = actorSystem.log

  val config = ConfigFactory.load("application.conf")

  val db: PostgresProfile.backend.Database = Database.forConfig("database", config)


  val wsRouter: ActorRef = actorSystem.actorOf(WsRouter.props)

  val routes = new Controller(wsRouter).build

  val host = config.getString("connection.host")
  val port = config.getInt("connection.port")

  Http()
    .bindAndHandle(routes, host, port)
    .map(_ => logger.info(s"server start ${host}:${port}"))


}
