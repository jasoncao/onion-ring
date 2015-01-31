package com.onion.mongo

import com.onion.model.{RegularType, Calender, Location, Meeting}
import org.scalatest.FunSuite
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
 * Created by famo on 1/28/15.
 */
class MeetingDaoTest extends FunSuite{

  val location = Location("haha_name","haha_gps","haha_city",123,123)
  val calender = Calender(123,123,RegularType.EveryDay,123,123)
  val meeting = Meeting("haha_id","0","haha_subject","haha_description","haha_target_user",12.34,calender,location,123,123)

  test("meeting dao test") {

    val meetingDao = DB.MeetingDao
    val result = meetingDao.add(meeting)
    Await.result(result, 100 days)


    val result2 = meetingDao.findById("0")
    println(Await.result(result2, 100 days))


  }
}
