package ru.example.blog

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.ActorRef
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.{Flow, GraphDSL, Keep, Sink, Source}
import akka.stream.{FlowShape, KillSwitches, OverflowStrategy}
import ru.example.blog.WsComment.NewComment
import ru.example.blog.WsRouter.NewPost
import ru.example.blog.model.{Comment, Post}
import ru.example.blog.repository.{CommentRepository, PostRepository, UserRepository}
import ru.example.blog.serialize.JsonFormats._
import spray.json._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ExecutionContext, Future}

class Controller(wsRoute: ActorRef,
                 wsComment: ActorRef,
                 userRepository: UserRepository,
                 postRepository: PostRepository,
                 commentRepository: CommentRepository
                )(implicit val ec: ExecutionContext) {

  type Response = Map[String, String]

  private val inc = new AtomicInteger(0)

  def build: Route = {

    pathPrefix("posts") {
      /*  /posts  */
      pathPrefix("user") {
        /*  posts/user  */
        path(IntNumber) { userId =>
          /*  posts/user/28091999  */
          get {
            val posts: Future[Seq[Post]] = postRepository.getAllUserPosts(userId)
            complete(posts)
          }
        } ~ {
          /*  /posts/user/ws  */
          pathPrefix("ws") {
            /*  /posts/user/ws/28091999  */
            path(IntNumber) { userId =>
              handleWebSocketMessages(websocket(userId))
            }
          }
        }
      } ~ path(IntNumber) { postId =>
        delete {
          postRepository.deletePost(postId)
          val response = Utils.buildSuccessResponse(s"Post has successfully deleted")
          complete(response)
        }
      } ~ pathEnd {
        (post & entity(as[Post])) { post =>
          postRepository.insertPost(post)
          wsRoute ! NewPost(post)
          val response = Utils.buildSuccessResponse("Post has inserted")
          complete(response)
        } ~ pathEnd {
          get {
            val posts: Future[Seq[Post]] = postRepository.getAllPosts
            complete(posts)
          }
        } ~ {
          (post & entity(as[Comment])) { comment =>
            commentRepository.insertComment(comment)
            wsComment ! NewComment(comment)
            val response = Utils.buildSuccessResponse("Comment has inserted")
            complete(response)
          }
        }
      }
    } ~ path(IntNumber / IntNumber) { (userId, postId) =>
      get {
        complete(s"UserId = $userId, PostId = $postId")
      } ~ handleWebSocketMessages(comments(userId, postId))
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

  private def comments(userId: Int, postId: Int): Flow[Message, Message, Any] = {
    Flow.fromGraph(GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      val flowClient = builder.add(Flow[Message].watchTermination() {
        case (_, done) => done.onComplete(_ => wsComment ! WsComment.Disconnect(userId, postId))
      })
      val sink = builder.add(Sink.ignore)

      flowClient ~> sink

      val source = builder.add(
        Source.actorRef[Comment](8, OverflowStrategy.fail)
          .viaMat(KillSwitches.single)(Keep.both)
          .mapMaterializedValue {
            case (actorRef, kill) =>
              wsComment ! WsComment.NewConnect(userId, postId, WsCommentSource(actorRef, kill))
          }
          .map(comment => TextMessage(entityToJson(comment)))
      )

      FlowShape(flowClient.in, source.out)
    })
  }

  private def entityToJson[A: JsonWriter](entity: A): String = {
    entity.toJson.prettyPrint
  }

}
