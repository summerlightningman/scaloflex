package ru.example.blog.model

import java.sql.Timestamp
import java.util.Date

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape


class PostTable(tag: Tag) extends Table[Post](tag, "post") {
  def postId: Rep[Int] = column[Int]("comment_id", O.PrimaryKey, O.AutoInc)

  def userId: Rep[Int] = column[Int]("user_id")

  def text: Rep[String] = column[String]("text")

  def dateTime: Rep[Timestamp] = column[Timestamp]("dateTime", O.Default(new Timestamp(System.currentTimeMillis())))

  override def * : ProvenShape[Post] = (postId.?, userId, text, dateTime).mapTo[Post]
}

