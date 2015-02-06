package com.onion.mongo

import com.onion.model.{Gender, User}
import org.scalatest.FunSuite
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by famo on 1/28/15.
 */
class UserDaoTest extends FunSuite {
  val user = User("id",
    "haha_city_id",
    "haha_name",
    "haha_password",
    "haha_phone",
    "haha_qq",
    "haha_email",
    "haha_photo",
    Gender.Male,
    "haha_job_title",
    "haha_description",
    10,
    123,
    123,
    false)

  test("user dao test") {
    val userDao = DB.UserDao
    val result = userDao.add(user)
    Await.result(result, 100 days)

//    val result2 = userDao.findById("0")
//    println(Await.result(result2, 100 days))
  }
}
