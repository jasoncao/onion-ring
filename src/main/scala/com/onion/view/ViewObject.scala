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

  type MeetingDetail = Meeting

  type UserDetail = User

  case class MeetingAbstraction(id: String, subject: String, desc: String, price: Double,
                                createTime: Long, updateTime: Long, hosterId: String, hosterDesc: String,
                                locationName: String)

  object MeetingAbstraction extends DefaultJsonProtocol {
    implicit val format = jsonFormat9(apply)

    implicit def fromModel(meeting: Meeting, user: User): MeetingAbstraction = {
      MeetingAbstraction(meeting.id, meeting.subject, meeting.description, meeting.price,
        meeting.createTime, meeting.updateTime, meeting.userId, user.description, meeting.location.name)
    }
  }

  case class MeetingAbsResponse(meetingAbsList: Iterable[MeetingAbstraction])

  object MeetingAbsResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)

    def fromModels(meetingsFuture: Future[Iterable[Meeting]]): Future[MeetingAbsResponse] = {
      //      val result = for {
      //        meetings <- meetingsFuture
      //        meeting <- meetings
      //        userOpt <- UserDao.findById(meeting.userId)
      //        user <- userOpt
      //      }
      //      yield {
      //        MeetingAbstraction
      //          .fromModel(meeting, user)
      //      }

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

}
