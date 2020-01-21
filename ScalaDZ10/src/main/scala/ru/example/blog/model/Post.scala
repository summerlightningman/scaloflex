package ru.example.blog.model

import java.security.Timestamp

case class Post(postId: Int, userId: Int, text: String, dateTime: Timestamp)
