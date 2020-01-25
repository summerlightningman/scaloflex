package ru.example.blog.model

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape


class PostTable(tag: Tag) extends Table[Post](tag,Some("scaladz"), "post") {
  def postId: Rep[Int] = column[Int]("post_id", O.PrimaryKey, O.AutoInc)

  def userId: Rep[Int] = column[Int]("user_id")

  def text: Rep[String] = column[String]("text")

  def dateTime: Rep[Timestamp] = column[Timestamp]("datetime", O.Default(new Timestamp(System.currentTimeMillis())))

  override def * : ProvenShape[Post] = (postId, userId, text, dateTime).mapTo[Post]
}

