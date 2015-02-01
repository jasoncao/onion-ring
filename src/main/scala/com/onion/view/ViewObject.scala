package com.onion.view

import com.onion.model._
import com.onion.mongo.DB.UserDao
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by jincao on 1/30/15.
 */
object ViewObject {


  type UserDetail = User

  type CalenderDetail = Calender

  type LocationDetail = Location

  case class UserAbstraction(id: String, name: String, title: String, icon: String, rating: Int)

  object UserAbstraction extends DefaultJsonProtocol {
    implicit val format = jsonFormat5(apply)
  }

  case class MeetingAbstraction(id: String, subject: String, target: String, desc: String, price: Double,
                                createTime: Long, updateTime: Long, seller: UserAbstraction)

  object MeetingAbstraction extends DefaultJsonProtocol {
    implicit val format = jsonFormat8(apply)

    implicit def fromModel(meeting: Meeting, user: User): MeetingAbstraction = {
      MeetingAbstraction(meeting.id, meeting.subject, meeting.targetUser, meeting.description, meeting.price,
        meeting.createTime, meeting.updateTime,
        UserAbstraction(user.id,user.name,user.jobTitle,user.photo,user.score))
    }
  }

  case class MeetingAbsResponse(meetings: Iterable[MeetingAbstraction])

  object MeetingAbsResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)

    def fromModels(meetingsFuture: Future[Iterable[Meeting]]): Future[MeetingAbsResponse] = {
      val result = for (meetings <- meetingsFuture)
      yield {
        val listOfFuture = for (meeting <- meetings)
        yield for (userOpt <- UserDao.findById(meeting.userId))
          yield for (user <- userOpt)
            yield MeetingAbstraction.fromModel(meeting, user)
        Future.sequence(listOfFuture).map(_.filter(_.isDefined).map(_.get))
      }

      result.flatMap(_.map(MeetingAbsResponse(_)))
    }
  }

  case class MeetingDetail(id: String,subject: String,target: String,desc: String,price: Double,createTime: Long, updateTime:Long,
                           seller: UserAbstraction, calender: CalenderDetail,location: LocationDetail)

}
