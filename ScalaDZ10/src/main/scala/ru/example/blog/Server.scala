package ru.example.blog

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import ru.example.blog.repository.{CommentRepository, PostRepository, UserRepository}
import slick.jdbc.PostgresProfile

object Server extends App {

  import slick.jdbc.PostgresProfile.api._

  implicit val actorSystem = ActorSystem("blog")
  implicit val materializer = ActorMaterializer()
  implicit val ec = actorSystem.dispatcher

  val logger = actorSystem.log

  val db: PostgresProfile.backend.Database = Database.forConfig("database")

  val wsRouter: ActorRef = actorSystem.actorOf(WsRouter.props)
  val wsComment: ActorRef = actorSystem.actorOf(WsComment.props)

  val userRepository = new UserRepository(db)
  val postRepository = new PostRepository(db)
  val commentRepository = new CommentRepository(db)

  val routes = new Controller(wsRouter, wsComment, userRepository, postRepository, commentRepository).build

  val host = actorSystem.settings.config.getString("connection.host")
  val port = actorSystem.settings.config.getInt("connection.port")

  Http()
    .bindAndHandle(routes, host, port)
    .map(_ => logger.info(s"server start ${host}:${port}"))


}
