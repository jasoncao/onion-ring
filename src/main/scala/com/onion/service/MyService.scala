package com.onion.service

import akka.actor.Actor
import com.onion.view.ViewObject.{PutBook, PostMeeting, PutMeeting}
import spray.httpx.SprayJsonSupport
import spray.routing.{Route, HttpService}
import scala.concurrent.ExecutionContext.Implicits.global
import com.onion.view.ViewController._

/**
 * Created by famo on 3/7/15.
 */
class MyServiceActor extends Actor with MyService {
  override def actorRefFactory = context

  override def receive = runRoute(meetingRoute)
}

trait MyService extends HttpService with SprayJsonSupport {
  val meetingRoute: Route =
    pathPrefix("api") {
      pathPrefix("v1") {
        path("meetings") {
          pathEnd {
            parameters("cityId" ? "0", "pageNum" ? 1) { (cityId: String, pageNum: Int) => {
              get {
                complete {
                  getMeetingsFromDB(cityId, pageNum)
                }
              }
            }
            }
          } ~
            put {
              entity(as[PutMeeting]) { putMeeting =>
                complete {
                  addMeetingToDB(putMeeting.meeting)
                }
              }
            }
        } ~
          path("meetings" / Segment) {
            meetingId =>
              get {
                complete {
                  getMeetingDetailFromDB(meetingId)
                }
              } ~
                post {
                  entity(as[PostMeeting]) { postMeeting =>
                    complete {
                      updateMeetingToDB(postMeeting.meeting)
                    }
                  }
                } ~
                delete {
                  complete {
                    deleteMeetingToDB(meetingId)
                  }
                }
//              path("actions") {
//                path("book"){
//                  put {
//                    entity(as[PutBook]) { putBook =>
//                      complete {
//
//                      }
//                    }
//                  }
//                }
//              }
          }
      }
    }
}
