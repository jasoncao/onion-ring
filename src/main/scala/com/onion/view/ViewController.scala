package com.onion.view

import com.onion.mongo.DB._
import com.onion.view.ViewObject.ResponseCode.{ERROR500, OK200}
import com.onion.view.ViewObject._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Promise, Future}
import scala.util.{Failure, Success}

/**
 * Created by jincao on 1/30/15.
 */
object ViewController {

  def getMeetingsFromDB(cityId: Option[String], pageNum: Option[Int]) : Future[ViewObject.MeetingAbsResponse] =
    (cityId, pageNum) match {
      case (None, None)       => MeetingAbsResponse.fromModels(MeetingDao.findByPage())
      case (Some(city),None) => MeetingAbsResponse.fromModels(MeetingDao.findByCityId(city))
      case (None, Some(page)) => MeetingAbsResponse.fromModels(MeetingDao.findByPage(page))
      case (Some(city), Some(page)) => MeetingAbsResponse.fromModels(MeetingDao.findByCityId(city,page))
    }

  def getMeetingDetailFromDB(id: String) = {
    MeetingResponse.fromModels(MeetingDao.findById(id))
  }

  def addMeetingToDB(meeting: Option[MeetingDetail]) : Future[PostResponse] = {
    val postResponse = Promise[PostResponse]()
    meeting match {
      case Some(meetingDetail) => MeetingDao.add(meetingDetail.toMeeting).onComplete {
        case Success(_) => postResponse success PostResponse(OK200,"success")
        case Failure(t) => postResponse success PostResponse(ERROR500,t.getMessage)

      }
    }
    postResponse.future
  }
}
