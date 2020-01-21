package ru.example.blog.repository

import ru.example.blog.model.{Post, PostTable}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration._

object PostRepository {
  val db = Database.forConfig("database")

  val posts = TableQuery[PostTable]

  def getAllUserPosts(userId: Int): Seq[Post] = {
    val select = for (post <- posts if post.userId === userId) yield post
    val result = db.run(select.result)
    Await.result(result, 1.second)
  }

  def insertPost(post: Post): Int = {
    val insert = posts += post
    val result = db.run(insert)
    Await.result(result, 1.second)
  }

  def getPost(postId: Int): Seq[Post] = {
    val select = for {post <- posts if post.postId === postId} yield post
    val result = db.run(select.result)
    Await.result(result, 1.second)
  }


  def getAllPosts: Seq[Post] = {
    val select = for {post <- posts} yield post
    val result = db.run(select.result)
    Await.result(result, 1.second)
  }

  def deletePost(postId: Int): Int = {
    val delete = posts.filter(_.postId === postId).delete
    val result = db.run(delete)
    Await.result(result, 1.second)
  }
}
