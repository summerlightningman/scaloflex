package ru.example.blog.model

import java.sql.Timestamp

case class Post(postId: Int, userId: Int, text: String, dateTime: Timestamp)
