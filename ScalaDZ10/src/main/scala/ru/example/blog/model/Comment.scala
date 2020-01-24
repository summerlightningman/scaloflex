package ru.example.blog.model

import java.sql.Timestamp

case class Comment(commentId: Int, postId: Int, toCommentId: Int, userId: Int, text: String, dateTime: Timestamp)
