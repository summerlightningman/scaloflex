package ru.example.blog.repository

import ru.example.blog.model.{Post, PostTable}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object PostRepository {
  val db = Database.forConfig("database")

  val posts = TableQuery[PostTable]

  def getAllUserPosts(userId: Int): Future[Seq[Post]] = {
    val select = for (post <- posts if post.userId === userId) yield post
    db.run(select.result)
  }

  def insertPost(post: Post): Future[Int] = {
    val insert = posts += post
    db.run(insert)
  }

  def getPost(postId: Int): Future[Seq[Post]] = {
    val post = for {post <- posts if post.postId === postId} yield post
    db.run(post.result)
  }


  def getAllPosts: Future[Seq[Post]] = {
    db.run(posts.result)
  }

  def deletePost(postId: Int): Future[Int] = {
    val delete = posts.filter(_.postId === postId).delete
    db.run(delete)
  }
}
