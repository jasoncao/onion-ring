package com.onion.mongo

import com.onion.model.{Calender, Location, Meeting}
import org.scalatest.FunSuite
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import com.onion.core.util.OptionUtil._

/**
 * Created by famo on 1/28/15.
 */
class MeetingDaoTest extends FunSuite{

  val locations = List(Location("123","haha_name","haha_gps"))
  val calenders = List(Calender("123",123L,123L))
  val meeting = Meeting(null,"haha_city","1423223642935-b3565b86-426e-48a0-b316-ed9335efbeff","haha_subject","haha_description","haha_target_user",12.34,calenders,locations,List(),123,123,false)

  test("meeting dao test") {

    val meetingDao = DB.MeetingDao
    val result = meetingDao.add(meeting)
    Await.result(result, 100 days)


//    val result2 = meetingDao.findById("0")
//    val result2 = meetingDao.findByCityId("haha_city")
//    println(Await.result(result2, 100 days))


  }
}
