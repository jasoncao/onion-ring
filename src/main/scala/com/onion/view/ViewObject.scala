package com.onion.view

import com.onion.model._
import com.onion.mongo.DB.UserDao
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.onion.core.util.FutureUtil._

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
        UserAbstraction(user.id, user.name, user.jobTitle, user.photo, user.score))
    }
  }

  case class MeetingAbsResponse(meetings: Iterable[MeetingAbstraction])

  object MeetingAbsResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)

    def fromModels(meetingsFuture: Future[Iterable[Meeting]]): Future[MeetingAbsResponse] = {
      meetingsFuture.scanIterable[MeetingAbstraction]((meeting: Meeting) => {
        UserDao.findById(meeting.userId).make[MeetingAbstraction](user => MeetingAbstraction.fromModel(meeting, user))
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

  /** ************************ todo line ****************************/
  case class CommentDetail(id: String, rating: Int, content: String, user: UserAbstraction)

  object CommentDetail extends DefaultJsonProtocol {
    implicit val format = jsonFormat4(apply)

    def fromModel(comment: Comment, user: User): CommentDetail = {
      CommentDetail(comment.id, comment.rating, comment.content, UserAbstraction(user.id, user.name, user.jobTitle, user.photo, user.score))
    }

  }

  case class MeetingDetail(id: String, cityId: String, subject: String, target: String, description: String, price: Double, createTime: Long, updateTime: Long,
                           seller: UserAbstraction, calenders: List[CalenderDetail], locations: List[LocationDetail], comments: List[CommentDetail])

  object MeetingDetail extends DefaultJsonProtocol {
    implicit val format = jsonFormat12(apply)

    def fromModels(meeting: Meeting, seller: User, comments: List[CommentDetail]) = {
      MeetingDetail(meeting.id, meeting.cityId, meeting.subject, meeting.targetUser, meeting.description, meeting.price, meeting.createTime, meeting.updateTime,
        UserAbstraction(seller.id, seller.name, seller.jobTitle, seller.photo, seller.score), meeting.calenders, meeting.locations, comments)
    }
  }

  case class MeetingResponse(meeting: MeetingDetail)

  object MeetingResponse extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(apply)

    def fromModels(meetingFuture: Future[Option[Meeting]]) = {
      meetingFuture
        .then(meeting => {
        UserDao.findById(meeting.userId)
          .then(seller => {
          meeting.comments.map(comment => {
            UserDao.findById(comment.userId).make(user => {
              CommentDetail.fromModel(comment, user)
            })
          }).make[MeetingDetail](comments => {
            MeetingDetail.fromModels(meeting, seller, comments.asInstanceOf[List[CommentDetail]])
          })
        })
      }).map(MeetingResponse(_))

      //      meetingFuture.flatMap[MeetingDetail] {
      //        case None => null // TODO: log error
      //        case Some(meeting) => UserDao.findById(meeting.userId).flatMap[MeetingDetail] {
      //          case None => null // TODO: log error
      //          case Some(seller) => {
      //            val cd: List[Future[CommentDetail]] = for (comment <- meeting.comments)
      //            yield CommentDetail.fromModel(comment)
      //            val commentDetail: Future[List[CommentDetail]] = Future.sequence(cd)
      //            commentDetail.map[MeetingDetail]((commentDetails: List[CommentDetail]) => MeetingDetail(meeting.id, meeting.subject, meeting.targetUser, meeting.description, meeting.price, meeting.createTime, meeting.updateTime,
      //              UserAbstraction(seller.id, seller.name, seller.jobTitle, seller.photo, seller.score), meeting.calenders, meeting.locations, commentDetails))
      //          }
      //        }
      //      }
    }
  }

}
