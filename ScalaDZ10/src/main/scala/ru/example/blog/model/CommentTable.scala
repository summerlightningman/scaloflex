package ru.example.blog.model

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape


class CommentTable(tag: Tag) extends Table[Comment](tag, "comment") {
  def commentId: Rep[Int] = column[Int]("comment_id", O.PrimaryKey, O.AutoInc)

  def postId: Rep[Int] = column[Int]("post_id")

  def toCommentId: Rep[Int] = column[Int]("tocomment_id")

  def userId: Rep[Int] = column[Int]("user_id")

  def text: Rep[String] = column[String]("text")

  def dateTime: Rep[Timestamp] = column[Timestamp]("datetime")

  override def * : ProvenShape[Comment] = (commentId.?, postId, toCommentId, userId, text, dateTime).mapTo[Comment]
}