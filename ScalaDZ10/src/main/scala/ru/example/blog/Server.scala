package ru.example.blog

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config._
import slick.jdbc.hikaricp.HikariCPJdbcDataSource.forConfig
import slick.util.ClassLoaderUtil

object Server extends App {

  implicit val actorSystem = ActorSystem("blog")
  implicit val materializer = ActorMaterializer()
  implicit val ec = actorSystem.dispatcher
  val logger = actorSystem.log

  val config = ConfigFactory.load("application.conf")

  val db = forConfig("database", config, ClassLoaderUtil.defaultClassLoader)

  val wsRouter: ActorRef = actorSystem.actorOf(WsRouter.props)

  val routes = new Controller(wsRouter).build

  val host = config.getString("connection.host")
  val port = config.getInt("connection.port")

  Http()
    .bindAndHandle(routes, host, port)
    .map(_ => logger.info(s"server start ${host}:${port}"))


}
