package com.onion.view

import com.onion.model.Meeting
import com.onion.mongo.DB._
import com.onion.view.ViewObject.ResponseCode.{ ERROR400, ERROR500, OK200 }
import com.onion.view.ViewObject._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ Promise, Future }
import scala.util.{ Failure, Success }
import com.onion.core.util.OptionUtil._
import com.onion.core.util.FutureUtil._

/**
 * Created by jincao on 1/30/15.
 */
object ViewController {

  def getMeetingsFromDB(cityId: Option[String], pageNum: Option[Int]): Future[ViewObject.MeetingAbsResponse] =
    (cityId, pageNum) match {
      case (None, None)             => MeetingAbsResponse.fromModels(MeetingDao.findByPage())
      case (Some(city), None)       => MeetingAbsResponse.fromModels(MeetingDao.findByCityId(city))
      case (None, Some(page))       => MeetingAbsResponse.fromModels(MeetingDao.findByPage(page))
      case (Some(city), Some(page)) => MeetingAbsResponse.fromModels(MeetingDao.findByCityId(city, page))
    }

  def getMeetingDetailFromDB(id: String) = {
    MeetingResponse.fromModels(MeetingDao.findById(id))
  }

  def addMeetingToDB(meeting: Option[MeetingDetail]): Future[PostResponse] = {
    val postResponse = Promise[PostResponse]()
    meeting match {
      case Some(meetingDetail) => MeetingDao.add(meetingDetail.toMeeting).onComplete {
        case Success(_) => postResponse success PostResponse(OK200, "success")
        case Failure(t) => postResponse success PostResponse(ERROR500, t.getMessage)
      }
      case None => postResponse success PostResponse(ERROR400, "request body empty")
    }
    postResponse.future
  }

  def updateMeetingToDB(meeting: Option[Meeting]): Future[PostResponse] = {
    MeetingDao.findById(meeting.id)
      .then(orginMeeting => {
        val postResponse = Promise[PostResponse]()
        val _meeting = meeting.map {
          (m: Meeting) =>
            val _locations = m.locations.getOrElse(List()).map(_.copy(id = newGuid))
            val _calenders = m.calenders.getOrElse(List()).map(_.copy(id = newGuid))
            orginMeeting.copy(
              cityId      = m.cityId,
              subject     = m.subject,
              target      = m.target,
              description = m.description,
              price       = m.price,
              locations   = _locations,
              calenders   = _calenders,
              updateTime  = System.currentTimeMillis
            )
        }
        _meeting match {
          case Some(m: Meeting) => MeetingDao.update(m).onComplete {
            case Success(_) => postResponse success PostResponse(OK200, "success")
            case Failure(t) =>
              postResponse success PostResponse(ERROR500, t.getMessage)
              println(t)
          }
          case None => postResponse success PostResponse(ERROR400, "request body empty")
        }
        postResponse.future
      }).get(PostResponse(ERROR400, "can't find meeting by id"))

  }
}
