package com.onion.view

import akka.http.model.StatusCodes
import com.onion.model.Meeting
import com.onion.mongo.DB._
import com.onion.view.ViewObject._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{ Failure, Success }

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

  def addMeetingToDB(meeting: MeetingDetail) = {
    val addFuture = MeetingDao.add(meeting.asInstanceOf[Meeting])
    addFuture.onComplete {
      case Success(m) => StatusCodes.Created
      case Failure(t) => StatusCodes.Conflict
    }
    addFuture
  }
}
