package ru.example.blog.model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape


class UserTable(tag: Tag) extends Table[User](tag, Some("scaladz"), "user") {
  def userId: Rep[Int] = column[Int]("user_id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  override def * : ProvenShape[User] = (userId, name).mapTo[User]
}