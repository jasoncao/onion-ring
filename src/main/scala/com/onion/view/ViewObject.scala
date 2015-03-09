package com.onion.view

import com.onion.model._
import com.onion.mongo.DB.UserDao
import spray.json.DefaultJsonProtocol
import sprest.util.enum.{EnumCompanion,Enum}

import scala.concurrent.Future
import com.onion.util.FutureUtil._
import com.onion.util.OptionUtil._
import scala.concurrent.ExecutionContext.Implicits.global

/**
* Created by famo on 1/30/15.
*/
object ViewObject {

  type UserDetail = User

  type CalenderDetail = Calender

  type LocationDetail = Location

  case class UserAbstraction(id: Option[String], name: Option[String], title: Option[String], icon: Option[String], rating: Option[Int])

  object UserAbstraction extends DefaultJsonProtocol {
    implicit val format = jsonFormat5(apply)
  }

  case class MeetingAbstraction(id: Option[String], subject: Option[String], target: Option[String], desc: Option[String], price: Option[Double],
                                createTime: Option[Long], updateTime: Option[Long], seller: Option[UserAbstraction])

  object MeetingAbstraction extends DefaultJsonProtocol {
    implicit val format = jsonFormat8(apply)

    implicit def fromModel(meeting: Meeting, user: User): MeetingAbstraction = {
      MeetingAbstraction(meeting.id, meeting.subject, meeting.target, meeting.description, meeting.price,
        meeting.createTime, meeting.updateTime,
        UserAbstraction(user.id, user.name, user.jobTitle, user.photo, user.score))
    }
  }

  case class MeetingAbsResponse(meetings: Option[Iterable[MeetingAbstraction]])

  object MeetingAbsResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)

    def fromModels(meetingsFuture: Future[Iterable[Meeting]]): Future[MeetingAbsResponse] = {
      meetingsFuture.scanIterable[MeetingAbstraction]((meeting: Meeting) => {
        UserDao.findById(meeting.userId).make[MeetingAbstraction](user => MeetingAbstraction.fromModel(meeting, user)).get(null)
      }).map(MeetingAbsResponse(_))
      //
      //
      //      val result = for (meetings <- meetingsFuture)
      //      yield {
      //        val listOfFuture = for (meeting <- meetings)
      //        yield for (userOpt <- UserDao.findById(meeting.userId))
      //          yield for (user <- userOpt)
      //            yield MeetingAbstraction.fromModel(meeting, user)
      //        Future.sequence(listOfFuture).map(_.filter(_.isDefined).map(_.get))
      //      }
      //
      //      result.flatMap(_.map(MeetingAbsResponse(_)))
    }
  }

  case class CommentDetail(id: Option[String], rating: Option[Int], content: Option[String], user: Option[UserAbstraction]) {
    def toComment: Comment = Comment(
      id,
      rating,
      content,
      user.id
    )
  }

  object CommentDetail extends DefaultJsonProtocol {
    implicit val format = jsonFormat4(apply)

    def fromModel(comment: Comment, user: User): CommentDetail = {
      CommentDetail(comment.id, comment.rating, comment.content, UserAbstraction(user.id, user.name, user.jobTitle, user.photo, user.score))
    }
  }

  case class MeetingDetail(id: Option[String], cityId: Option[String], subject: Option[String], target: Option[String],
                           description: Option[String], price: Option[Double], createTime: Option[Long], updateTime: Option[Long],
                           seller: Option[UserAbstraction], calenders: Option[List[CalenderDetail]], locations: Option[List[LocationDetail]], comments: Option[List[CommentDetail]]) {
    def toMeeting: Meeting =
      Meeting(
        id,
        cityId,
        seller.id,
        subject,
        description,
        target,
        price,
        calenders,
        locations,
        comments.getOrElse(List()).map(_.toComment),
        createTime,
        updateTime,
        false
      )
  }

  object MeetingDetail extends DefaultJsonProtocol {
    implicit val format = jsonFormat12(apply)

    def fromModels(meeting: Meeting, seller: User, comments: List[CommentDetail]) = {
      MeetingDetail(meeting.id, meeting.cityId, meeting.subject, meeting.target, meeting.description, meeting.price, meeting.createTime, meeting.updateTime,
        UserAbstraction(seller.id, seller.name, seller.jobTitle, seller.photo, seller.score), meeting.calenders, meeting.locations, comments)
    }
  }

  case class MeetingResponse(meeting: Option[MeetingDetail])

  object MeetingResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)

    def fromModels(meetingFuture: Future[Option[Meeting]]) = {
      meetingFuture
        .to(meeting => {
          UserDao.findById(meeting.userId)
            .to(seller => {
              meeting.comments.get.map(comment => {
                UserDao.findById(comment.userId).make(user => {
                  CommentDetail.fromModel(comment, user)
                })
              }).make[MeetingDetail](comments => {
                MeetingDetail.fromModels(meeting, seller, comments.asInstanceOf[List[CommentDetail]])
              })
            })
        }).map(MeetingResponse(_))
    }
  }

  case class PutMeeting(meeting: Option[MeetingDetail])

  object PutMeeting extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)
  }

  sealed abstract class ResponseCode(val id: String) extends Enum[ResponseCode](id)

  object ResponseCode extends EnumCompanion[ResponseCode] {
    case object OK200 extends ResponseCode("200")
    case object ERROR400 extends ResponseCode("400")
    case object ERROR500 extends ResponseCode("500")

    register(
      OK200,
      ERROR400,
      ERROR500
    )
  }

  case class PostResponse(code: ResponseCode, msg: String)

  object PostResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(apply)
  }

  case class PostMeeting(meeting: Option[Meeting])

  object PostMeeting extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)
  }

}
