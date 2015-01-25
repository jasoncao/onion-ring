package onionring.service

import akka.actor.{Props, Actor}
import akka.pattern.ask
import onionring.dao.{GetUser, UserDao, Get, MeetingDao}
import onionring.pojo.{User, Listing}
import onionring.vo._
import scala.concurrent.duration._

import scala.concurrent.Await

/**
* Created by famo on 1/25/15.
*/
class MeetingService extends Actor{
  lazy val meetingDao = context.system.actorOf(Props(new MeetingDao))
  lazy val userDao = context.system.actorOf(Props(new UserDao))

  override def receive = {
    case _ =>
//    case GetMeetingList(cityId: Long, offset:Long, limit: Long) => {
      // TODO query by city id
//      val meeting = meetingDao ? Get(offset,limit)
//      meeting.onSuccess{
//        case result : List[Listing] =>
//          sender ! result.map(meeting => {
//            val userFuture = userDao ? GetUser(meeting.userId)
//            val user = Await.result(userFuture, 100 days).asInstanceOf[User]
//            MeetingListResponse(payload =
//              val paging = Paging(offset,limit)
//              val meetingMeetingListResponseMeetingList(
//              MeetingSummary(meeting.listingId,
//              meeting.subject,
//              meeting.targetUser,
//              meeting.money,
//              meeting.createTime
//              ),UserSummary(
//                user.userId,
//                user.name,
//                user.jobTitle,
//                user.photo,
//                user.score
//                )
//              )
//            )
//          })
//      }
//    }
  }
}

case class GetMeetingList(cityId: Long, offset:Long, limit: Long)
