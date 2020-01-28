package ru.example.blog.serialize

import java.sql.Timestamp

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import ru.example.blog.model.{Comment, Post}
import spray.json.{DefaultJsonProtocol, JsNumber, JsValue, RootJsonFormat}

object JsonFormats extends DefaultJsonProtocol with SprayJsonSupport {


  implicit object TimestampSerializer extends RootJsonFormat[Timestamp] {
    override def read(json: JsValue): Timestamp = json match {
      case JsNumber(value) => new Timestamp(value.toLong)
      case _ => new Timestamp(0)
    }

    override def write(obj: Timestamp): JsValue = {
      JsNumber(obj.getTime)
    }
  }

  implicit val postFormat: RootJsonFormat[Post] = jsonFormat4(Post)
  implicit val commentFormat: RootJsonFormat[Comment] = jsonFormat6(Comment)

}