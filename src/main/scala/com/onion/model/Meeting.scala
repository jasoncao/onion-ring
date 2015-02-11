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
  id:      Option[String],
  rating:  Option[Int],
  content: Option[String],
  userId:  Option[String]
)

object Comment {
  implicit val reminderJsonFormat = jsonFormat4(apply)
}

case class Calender(
  id:       Option[String],
  fromTime: Option[Long],
  toTime:   Option[Long]
)

object Calender {
  implicit val reminderJsonFormat = jsonFormat3(apply)
}

case class Location(
  id:   Option[String],
  name: Option[String],
  gps:  Option[String]
)

object Location {
  implicit val reminderJsonFormat = jsonFormat3(apply)
}

case class Meeting(
  id:          String,
  cityId:      Option[String],
  userId:      Option[String],
  subject:     Option[String],
  description: Option[String],
  target:      Option[String],
  price:       Option[Double],
  calenders:   Option[List[Calender]],
  locations:   Option[List[Location]],
  comments:    Option[List[Comment]],
  createTime:  Option[Long],
  updateTime:  Option[Long],
  isDeleted:   Option[Boolean]
) extends Model[String]

object Meeting {
  implicit val reminderJsonFormat = jsonFormat13(apply)
}