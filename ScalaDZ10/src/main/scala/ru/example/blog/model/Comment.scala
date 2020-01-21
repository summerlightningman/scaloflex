package ru.example.blog.model

import java.security.Timestamp

case class Comment(commentId: Int, postId: Int, toCommentId: Int, userId: Int, text: Int, dateTime: Timestamp)
