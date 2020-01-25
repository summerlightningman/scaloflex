package ru.example.blog.repository

import ru.example.blog.model.{User, UserTable}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

class UserRepository(db: Database) {

  private val users = TableQuery[UserTable]

  def insertUser(user: User): Future[Int] = {
    val insertAction = users += User(0, user.name)
    db.run(insertAction)
  }

  def getAllUsers: Future[Seq[User]] = {
    val select = for {user <- users} yield user
    db.run(select.result)
  }

  def getUser(userId: Int): Future[User] = {
    val user = users.filter(_.userId === userId)
    db.run(user.result.head)
  }


}

