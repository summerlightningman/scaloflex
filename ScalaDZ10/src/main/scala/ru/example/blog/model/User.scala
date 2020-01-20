package ru.example.blog.model

import slick.driver.H2Driver.api._

case class User(id: Int, name: String)
//class User(tag: Tag) extends Table[(Int, String)](tag) {
//  def userId: Rep[Int] = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
//  def text = column[String]("text")
//}