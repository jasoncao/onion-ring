package onionring.controller

import akka.actor.{Props, ActorSystem, Actor}
import akka.http.Http
import akka.http.server.{Route, Directives}
import akka.stream.scaladsl.ImplicitFlowMaterializer
import onionring.vo._
import spray.json._
import de.heikoseeberger.reactiveflows.util.SprayJsonMarshalling
import onionring.vo.MeetingVO._
import akka.pattern.ask

/**
 * Created by famo on 1/24/15.
 */


class MeetingController
  extends Actor
  with Directives
  with ImplicitFlowMaterializer
  with SprayJsonMarshalling
  with DefaultJsonProtocol {

  import context.dispatcher

  override def receive: Receive =
    Actor.emptyBehavior

  Http()(context.system)
    .bind("127.0.0.1", 8080)
    .startHandlingWith(route)

  private def route: Route = {
    path("meetingList") {
      post {
        entity(as[MeetingListRequest]) { request =>
          complete {
            println(request.payload.cityId)
            val paging = Paging(-1, 0)
            val meetingSummary = MeetingSummary(-1, "haha", "haha", 0.1, "haha", "haha")
            val userSummary = UserSummary(-1, "haha", "haha", "haha", -1)
            val meetingList = MeetingListResponseMeetingList(meetingSummary, userSummary)
            val payload = MeetingListResponsePayload(paging, List(meetingList))
            MeetingListResponse(EmptyHeader(), payload).toJson.toString()
          }
        }
      }
    }
  }

}

object Test extends App {
  val system = ActorSystem("haha")
  system.actorOf(Props(new MeetingController))
}

