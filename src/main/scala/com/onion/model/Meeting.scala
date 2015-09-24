package com.onion.model

import sprest.models.Model
import sprest.Formats._

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

case class Selection(
  id:          Option[String],
  time:        Option[Long],
  duration:    Option[Long],
  address:     Option[String],
  gps:         Option[String]
)

object Selection{
  implicit val reminderJsonFormat = jsonFormat5(apply)
}

case class Meeting(
  id:          String,
  cityId:      Option[String],
  userId:      Option[String],
  subject:     Option[String],
  description: Option[String],
  target:      Option[String],
  price:       Option[Double],
  selection:   Option[List[Selection]],
  comments:    Option[List[Comment]],
  createTime:  Option[Long],
  updateTime:  Option[Long],
  isDeleted:   Option[Boolean]
) extends Model[String]

object Meeting {
  implicit val reminderJsonFormat = jsonFormat12(apply)
}