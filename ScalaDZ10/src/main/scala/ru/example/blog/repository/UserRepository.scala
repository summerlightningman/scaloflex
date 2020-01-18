package ru.example.blog.repository

import ru.example.blog.model.User

import scala.collection.mutable.ListBuffer

object UserRepository {
  private val users = ListBuffer[User]()

  def insertUser(user: User): Unit = {
    users.append(user)
  }

  def getAllUsers: Seq[User] = {
    users
  }

  def getUser(userId: Int): Either[String, User] = {
    users.find(_.id == userId) match {
      case Some(user: User) => Right(user)
      case None => Left(s"User $userId not found")
    }
  }

}
