package ru.example.blog.repository

import ru.example.blog.model.Comment

import scala.collection.mutable.ListBuffer

object CommentRepository {
  private val comments = ListBuffer[Comment]()

  def insertComment(comment: Comment): Unit = {
    comments.append(comment)
  }

  def getAllPostComments(postId: Int): ListBuffer[Comment] = {
    comments.filter(_.postId == postId)
  }
}
