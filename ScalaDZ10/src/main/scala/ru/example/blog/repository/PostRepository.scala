package ru.example.blog.repository

import ru.example.blog.model.{Comment, Post, User}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object PostRepository {

  private val posts = ListBuffer[Post]()

  def getAllUserPosts(userId: Int): mutable.Seq[Post] = {
    posts.filter(_.userId == userId)
  }

  def insertPost(post: Post): String = {
    // validators
    UserRepository.getUser(post.userId) match {
      case user: User =>
        if (post.text.length > 200)
          s"Post shouldn't have more 200 characters"
        else {
          posts.append(post)
          s"Post has been added successfully"
        }
      case _ =>
        s"User ${post.userId} not found"
    }
  }

  def getPost(postId: Int): Either[String, (Post, ListBuffer[Comment])] = {
    posts.find(_.id == postId) match {
      case Some(post: Post) => Right((post, CommentRepository.getAllPostComments(post.id)))
      case None => Left("Post $postId not found")
    }
  }


  def getAllPosts: Seq[Post] = {
    posts
  }

  def deletePost(postId: Int): String = {
    posts.find(_.id == postId) match {
      case Some(post: Post) => posts -= post
        s"Post $postId has successfully deleted"
      case None =>
        s"Post $postId not found"
    }

  }
}
