package ru.example.blog.repository

import ru.example.blog.model.{Comment, CommentTable}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object CommentRepository {
  val db = Database.forConfig("database")
  private val comments = TableQuery[CommentTable]


  def insertComment(comment: Comment): Int = {
    val insert = comments += comment
    val result = db.run(insert)
    Await.result(result, 1.second)
  }

  def getAllPostComments(postId: Int): Seq[Comment] = {
    val select = for {comment <- comments if comment.postId === postId} yield comment
    val result = db.run(select.result)
    Await.result(result, 1.second)
  }
}
