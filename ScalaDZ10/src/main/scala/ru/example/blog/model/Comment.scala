package ru.example.blog.model

import java.sql.Timestamp

case class Comment(commentId: Int, postId: Int, toCommentId: Option[Int], userId: Int, text: String, dateTime: Timestamp)