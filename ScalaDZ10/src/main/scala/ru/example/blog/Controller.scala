package ru.example.blog

import java.sql.Statement
import java.util.concurrent.atomic.AtomicInteger

import akka.actor.ActorRef
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.{Flow, GraphDSL, Keep, Sink, Source}
import akka.stream.{FlowShape, KillSwitches, OverflowStrategy}
import ru.example.blog.WsRouter.NewPost
import ru.example.blog.model.Post
import ru.example.blog.repository.PostRepository
import ru.example.blog.serialize.JsonFormats._
import spray.json._

import scala.concurrent.ExecutionContext

class Controller(wsRoute: ActorRef)(implicit val ec: ExecutionContext) {

  type Response = Map[String, String]

  val listPosts: Seq[Post] => HttpResponse = entityToJson[Seq[Post]] _ andThen Utils.buildSuccessResponse

  private val inc = new AtomicInteger(0)

  def build: Route = {

    pathPrefix("posts") {
      /*  /posts  */
      pathPrefix("user") {
        /*  posts/user  */
        path(IntNumber) { userId =>
          /*  posts/user/28091999  */
          get {
            val posts = PostRepository.getAllUserPosts(userId)
            //            val result: Seq[Post] => HttpResponse = entityToJson[Seq[Post]] _ andThen Utils.buildSuccessResponse
            complete(listPosts(posts))
          }
        } ~ {
          /*  /posts/user/ws  */
          pathPrefix("ws") {
            /*  /posts/user/28091999  */
            path(IntNumber) { userId =>
              handleWebSocketMessages(websocket(userId))
            }
          }
        }
      } ~ {
        /*  /posts/12345  */
        path(IntNumber) { postId =>
          delete {
            val result = PostRepository.deletePost(postId)
            complete(result)
          }
        }
      /*  /posts  */
      } ~ pathEnd {
        (post & entity(as[Post])) { post =>
          PostRepository.insertPost(post)
          wsRoute ! NewPost(post)
          val response = Utils.buildSuccessResponse("post was inserted")
          complete(response)
        } ~ pathEnd {
          get {
            val posts = PostRepository.getAllPosts
            //          val result: Seq[Post] => HttpResponse = entityToJson[Seq[Post]] _ andThen Utils.buildSuccessResponse
            complete(listPosts(posts))
          }
        }
      }

    }

  }

  private def websocket(userId: Int): Flow[Message, Message, Any] = {
    val connectionId = inc.getAndIncrement()
    Flow.fromGraph(GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._


      val flowClient = builder.add(Flow[Message].watchTermination() { case (_, done) =>
        done.onComplete(_ => wsRoute ! WsRouter.Disconnect(userId, connectionId))
      })
      val sink = builder.add(Sink.ignore)

      flowClient ~> sink

      val source = builder.add(Source
        .actorRef[Post](8, OverflowStrategy.fail)
        .viaMat(KillSwitches.single)(Keep.both)
        .mapMaterializedValue { case (actorRef, kill) =>
          wsRoute ! WsRouter.NewConnect(userId, connectionId, WsSource(actorRef, kill))
        }
        .map(post => TextMessage(entityToJson(post)))
      )

      FlowShape(flowClient.in, source.out)
    })

  }

  private def entityToJson[A: JsonWriter](entity: A): String = {
    entity.toJson.prettyPrint
  }

}
