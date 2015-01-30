package com.onion.view

import akka.http.marshalling.ToResponseMarshallable
import com.onion.model._
import spray.json.DefaultJsonProtocol

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

  case class MeetingAbsResponse(meetingAbsList: Array[MeetingAbstraction])

  object MeetingAbsResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)
  }

  def getMeetingsFromDB(cityId: Option[String]): ToResponseMarshallable = ???

  def getMeetingDetaillFromDB(id: String): ToResponseMarshallable = ???

  def addMeetingToDB(meeting: Meeting): ToResponseMarshallable = ???

}