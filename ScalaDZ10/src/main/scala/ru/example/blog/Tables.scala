package ru.example.blog

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

trait Tables {

  class Post(tag: Tag) extends Table[(Int, Int, String, Timestamp)](tag, "post") {
    def postId: Rep[Int] = column[Int]("comment_id", O.PrimaryKey, O.AutoInc)

    def userId: Rep[Int] = column[Int]("user_id")

    def text: Rep[String] = column[String]("text")

    def dateTime: Rep[Timestamp] = column[Timestamp]("dateTime")

    override def * : ProvenShape[(Int, Int, String, Timestamp)] = (postId, userId, text, dateTime)
  }

  class UserTable(tag: Tag) extends Table[(Int, String)](tag, "user") {
    def userId: Rep[Int] = column[Int]("user_id", O.PrimaryKey, O.AutoInc)

    def text = column[String]("text")

    override def * : ProvenShape[(Int, String)] = (userId, text)
  }

  class Comment(tag: Tag) extends Table[(Int, Int, Int, Int, String, Timestamp)](tag, "comment") {
    def commentId: Rep[Int] = column[Int]("comment_id", O.PrimaryKey, O.AutoInc)

    def postId: Rep[Int] = column[Int]("post_id")

    def toCommentId: Rep[Int] = column[Int]("tocomment_id")

    def userId: Rep[Int] = column[Int]("user_id")

    def text: Rep[String] = column[String]("text")

    def dateTime: Rep[Timestamp] = column[Timestamp]("datetime")

    override def * : ProvenShape[(Int, Int, Int, Int, String, Timestamp)] = (commentId, postId, toCommentId, userId, text, dateTime)
  }

}
