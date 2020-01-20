package ru.example.blog.model
import slick.driver.H2Driver.api._
import java.sql.Timestamp

case class Post(id: Int, userId: Int, text: String, dateTime: Timestamp)
