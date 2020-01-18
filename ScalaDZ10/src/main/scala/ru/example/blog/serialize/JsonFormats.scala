package ru.example.blog.serialize

import java.sql.Timestamp

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import ru.example.blog.Config
import ru.example.blog.model.Post
import spray.json._

object JsonFormats extends DefaultJsonProtocol with SprayJsonSupport {

  implicit object ConfigSerializer extends RootJsonFormat[Config] {
    override def write(config: Config): JsValue = {
      JsObject(
        "host" -> config.host.toJson,
        "port" -> config.port.toJson
      )
    }

    override def read(json: JsValue): Config = {
      val fields = json.asJsObject("Config object expected").fields
      Config(
        host = fields("host").convertTo[String],
        port = fields("port").convertTo[Int]
      )
    }
  }

  implicit object TimestampSerializer extends RootJsonFormat[Timestamp] {
    override def read(json: JsValue): Timestamp = {
      json match {
        case JsNumber(value) => new Timestamp(value.toLong)
      }
    }

    override def write(obj: Timestamp): JsValue = {
      JsNumber(obj.getTime)
    }
  }

  implicit val postFormat: RootJsonFormat[Post] = jsonFormat4(Post)

}
