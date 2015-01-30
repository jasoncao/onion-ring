package com.onion

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.Http
import akka.http.server._
import akka.stream.scaladsl.ImplicitFlowMaterializer
import akka.util.Timeout
import com.onion.core.util.SprayJsonMarshalling
import com.onion.view.ViewObject._
import com.onion.view.ViewController._
import spray.json._

import scala.concurrent.duration._

/**
 * Created by jincao on 1/24/15.
 */

object HttpService {

  private case object Shutdown

  def props(interface: String, port: Int, askTimeout: FiniteDuration): Props =
    Props(new HttpService(interface, port)(askTimeout))
}

class HttpService(interface: String, port: Int)(implicit askTimeout: Timeout)
  extends Actor
  with ActorLogging
  with Directives
  with ImplicitFlowMaterializer
  with SprayJsonMarshalling
  with DefaultJsonProtocol {

  import com.onion.HttpService._
  import context.dispatcher

  Http()(context.system)
    .bind(interface, port)
    .startHandlingWith(route)

  log.info(s"Listening on $interface:$port")
  log.info(s"To shutdown, send GET request to http://$interface:$port/shutdown")

  override def receive: Receive =
    Actor.emptyBehavior

  private def route: Route =
    shutdown ~ meetings

  private def shutdown: Route =
    path("shutdown") {
      get {
        complete {
          context.system.scheduler.scheduleOnce(500 millis, self, Shutdown)
          log.info("Shutting down now ...")
          "Shutting down now ..."
        }
      }
    }

  private def meetings: Route =
    path("meetings") {
      pathEnd {
        get {
          complete {
            getMeetingsFromDB(None)
          }
        } ~
          post {
            entity(as[MeetingDetail]) {
              md =>
                complete {
                  addMeetingToDB(md)
                }
            }
          }
      }
    } ~
      path("meetings" / Segment) {
        meetingId => get {
          complete {
            getMeetingDetailFromDB(meetingId)
          }
        }
      } ~
      path("meetings") {
        parameters('cityId.as[String]) {
          (cityId) => get {
            complete {
              getMeetingsFromDB(Option(cityId))
            }
          }
        }
      }

}

