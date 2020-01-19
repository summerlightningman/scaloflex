package ru.example.blog.repository

import ru.example.blog.model.Comment
import java.util.concurrent.atomic.{AtomicReference, AtomicReferenceArray}

import scala.collection.mutable.ListBuffer

object CommentRepository {
  private val comments = ListBuffer[Comment]()
  private val cache = new AtomicReference[Comment]()


  def insertComment(comment: Comment): Unit = {
    comments.append(comment)

  }

  def getAllPostComments(postId: Int): ListBuffer[Comment] = {
    comments.filter(_.postId == postId)
//    comments.get()
  }
}
