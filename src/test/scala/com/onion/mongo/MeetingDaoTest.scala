package com.onion.mongo

import com.onion.models.{RegularType, Calender, Location, Meeting}
import org.scalatest.FunSuite
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import com.onion.mongo.DB.MeetingDao._

/**
 * Created by famo on 1/28/15.
 */
class MeetingDaoTest extends FunSuite {

  val location = Location("haha_name", "haha_gps", "haha_city", 123, 123)
  val calender = Calender(123, 123, RegularType.EveryDay, 123, 123)
  val meeting = Meeting("haha_id", "haha_user_id", "haha_subject", "haha_description", "haha_target_user", 12.34, calender, location, 123, 123)

  val findMeeting = Meeting("0", null, null, null, null, 0, null, null, 0, 0)

  test("meeting dao test") {

    val meetingDao = DB.MeetingDao
//    val result = meetingDao.add(meeting)
    //    Await.result(result, 100 days)


    val result = meetingDao.findOne[Meeting](findMeeting)
    println(Await.result(result, 100 days))
  }
}
