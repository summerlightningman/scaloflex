package ru.example.blog

import akka.actor.{Actor, ActorLogging, Props}
import ru.example.blog.model.Comment

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class WsComment extends Actor with ActorLogging {

  import WsComment._

  type PostId = Int
  type UserId = Int

  private val connections = mutable.HashMap[PostId, Map[UserId, WsCommentSource]]()

  override def receive: Receive = {
    case NewConnect(userId, postId, wsCommentSource) =>
      if (connections(postId).contains(userId))
        log.info(s"Error. User $userId already connected")
      else {
        connections(postId) += (userId -> wsCommentSource)
        log.info(s"User $userId was connected to post $postId")
      }

    case Disconnect(userId, postId) =>
      connections(postId) -= userId
      log.info(s"User $userId was disconnected from post $postId")

    case NewComment(comment) =>
      connections.get(comment.postId)
        .foreach(_.foreach(_._2.source ! comment))
  }
}

object WsComment {
  def props = Props(new WsComment())

  case class NewConnect(userId: Int, postId: Int, wsCommentSource: WsCommentSource)

  case class Disconnect(userId: Int, postId: Int)

  case class NewComment(comment: Comment)

}
