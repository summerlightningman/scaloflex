package ru.example.blog

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, ResponseEntity, StatusCode, StatusCodes}

object Utils {

  def buildSuccessResponse(data: String) = {
    buildHttpResponse(StatusCodes.OK, data)
  }

  def buildErrorResponse(data: String) = {
    buildHttpResponse(StatusCodes.InternalServerError, data)
  }


  private def buildHttpResponse(status: StatusCode, data: String) = {
    HttpResponse(
      status = status,
      entity = HttpEntity(
        contentType = ContentTypes.`text/plain(UTF-8)`,
        data
      )
    )
  }


}
