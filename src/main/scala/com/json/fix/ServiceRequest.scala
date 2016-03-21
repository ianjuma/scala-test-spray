package com.json.fix

import akka.actor.{Actor, ActorLogging}
import spray.client.pipelining._
import spray.http.{HttpRequest, HttpResponse}
import spray.httpx.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, _}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import SprayJsonSupport._

case class Message(from: String, to: String, text: String, date: String, id: String)

case class ApiResponse(response: String)
case class ApiRequest(request: String)
case object Poll

object JsonElementFormat extends DefaultJsonProtocol {
  implicit val apiRequestFormat = jsonFormat1(ApiRequest)
  implicit val apiResponseFormat = jsonFormat1(ApiResponse)
  implicit val messageFormat = jsonFormat5(Message)
}

class ServiceRequest extends Actor with ActorLogging {

  def receive: Receive = {
    case Poll =>
      import JsonElementFormat._

      val message = Message(
        from = "+254701435178",
        to = "20880",
        text = "winning Hello, AT testing",
        date = "2016-03-03 00:45:00",
        id = "ATx_2343523543624534636346"
      )

      val logRequest: HttpRequest => HttpRequest = {
        r => log.debug(r.toString)
        r
      }
      val logResponse: HttpResponse => HttpResponse = { 
        r => log.debug(r.toString)
        r
      }

      val pipeline: HttpRequest => Future[ApiResponse] = (
        addHeader("Accept", "application/json")
          ~>logRequest
          ~> sendReceive
          ~> logResponse
          ~> unmarshal[ApiResponse]
        )

      val urls = List(
        "https://cloud.frontlinesms.com/api/1/customFconnection/4830"
      )

      urls foreach(url => {
          val response: Future[ApiResponse] = pipeline {
            Post(url, message)
          }

          response onComplete {
            case Success(x) =>
              log.info("Passed")
              log.info(s"$x")

            case Failure(e) =>
              log.info("Failed")
              log.error(s"$e")
          }
        }
      )
  }
}
