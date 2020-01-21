package ru.example.blog.repository

import ru.example.blog.model.{User, UserTable}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object UserRepository {
  val db = Database.forConfig("database")

  private val users = TableQuery[UserTable]

  def insertUser(user: User): Int = {
    val insertAction = users += User(0, user.name)
    val result = db.run(insertAction)
    Await.result(result, 1.second)
  }

  def getAllUsers: Seq[User] = {
    val select = for {user <- users} yield user
    val result = db.run(select.result)
    Await.result(result, 1.second)
  }

  def getUser(userId: Int): Seq[User] = {
    val select = users.filter(_.userId === userId)
    val result = db.run(select.result)
    Await.result(result, 1.second)
  }


}

