package com.onion.view

import akka.http.model.StatusCodes
import com.onion.model.Meeting
import com.onion.mongo.DB._
import com.onion.view.ViewObject._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Created by jincao on 1/30/15.
 */
object ViewController {

  def getMeetingsFromDB(cityId: Option[String]) =
    cityId match {
      case None => MeetingAbsResponse.fromModels(MeetingDao.all)
      case Some(city) => Future.successful((StatusCodes.NotImplemented))
    }

  def getMeetingDetailFromDB(id: String) = {
    MeetingDao.findById(id).map(meeting => StatusCodes.OK -> meeting.asInstanceOf[MeetingDetail])
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
