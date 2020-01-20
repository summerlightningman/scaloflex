package ru.example.blog.model

import java.sql.Timestamp

import slick.driver.H2Driver.api._

import slick.lifted.ProvenShape

//case class Comment(commentId: Int, postId: Int, toCommentId: Option[Int], userId: Int, text: String, dateTime: Timestamp)
class Comment(tag: Tag) extends Table[(Int, Int, Option[Int], Int, String, Timestamp)](tag, "comment") {
  def commentId: Rep[Int] = column[Int]("comment_id", O.PrimaryKey, O.AutoInc)

  def postId: Rep[Int] = column[Int]("post_id")

  def toCommentId: Rep[Option[Int]] = column[Option[Int]]("tocomment_id")

  def userId: Rep[Int] = column[Int]("user_id")

  def text: Rep[String] = column[String]("text")

  def dateTime: Rep[Timestamp] = column[Timestamp]("datetime")

  override def * : ProvenShape[(Int, Int, Option[Int], Int, String, Timestamp)] = (commentId, postId, toCommentId, userId, text, dateTime)
}