package com.onion.model

import com.onion.core.models.Model
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

case class Comment(
  id:        String,
  rating:    Int,
  content:   String,
  userId:    String
)

object Comment {
  implicit val reminderJsonFormat = jsonFormat4(apply)
}

case class Calender(
  id:        String,
  fromTime:  Long,
  toTime:    Long
)

object Calender {
  implicit val reminderJsonFormat = jsonFormat3(apply)
}

case class Location(
  id:         String,
  name:       String,
  gps:        String
)

object Location {
  implicit val reminderJsonFormat = jsonFormat3(apply)
}

case class Meeting(
  id:          String,
  cityId:      String,
  userId:      String,
  subject:     String,
  description: String,
  targetUser:  String,
  price:       Double,
  calenders:   List[Calender],
  locations:   List[Location],
  comments:    List[Comment],
  createTime:  Long,
  updateTime:  Long,
  isDeleted:   Boolean
) extends Model[String]

object Meeting {
  implicit val reminderJsonFormat = jsonFormat13(apply)
}