package ru.example.blog.repository

import ru.example.blog.model.{Comment, CommentTable}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

class CommentRepository(db: Database) {

  private val comments = TableQuery[CommentTable]

  def insertComment(comment: Comment): Future[Int] = {
    val insert = comments += comment
    db.run(insert)
  }

  def getAllPostComments(postId: Int): Future[Seq[Comment]] = {
    val select = for {comment <- comments if comment.postId === postId} yield comment
    db.run(select.result)
  }
}
