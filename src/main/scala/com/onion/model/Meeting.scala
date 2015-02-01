package com.onion.model

import com.onion.core.models.Model
import com.onion.core.util.enum._
import com.onion.core.Formats._

/**
 * Created by famo on 1/27/15.
 */
// don't need it now
//sealed abstract class RegularType(val id: String, val label: String) extends Enum[RegularType](id)
//
//object RegularType extends EnumCompanion[RegularType] {
//
//  case object OneDay extends RegularType("oneDay", "one day")
//
//  case object EveryDay extends RegularType("everyDay", "every day")
//
//  register(
//    OneDay,
//    EveryDay
//  )
//}

case class Calender(
  id:          String,
  startTime:   Long,
  duration:    Long
//  regularType: RegularType = RegularType.OneDay,
)

object Calender {
  implicit val reminderJsonFormat = jsonFormat3(apply)
}

case class Location(
  id:         String,
  name:       String,
  gps:        String,
  cityId:     String
)

object Location {
  implicit val reminderJsonFormat = jsonFormat4(apply)
}

case class Meeting(
  id:          String,
  userId:      String,
  subject:     String,
  description: String,
  targetUser:  String,
  price:       Double,
  calender:    Calender,
  location:    Location,
  createTime:  Long,
  updateTime:  Long
) extends Model[String]

object Meeting {
  implicit val reminderJsonFormat = jsonFormat10(apply)
}