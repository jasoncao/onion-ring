package com.onion.models

import com.onion.core.models.Model
import com.onion.core.util.enum._
import com.onion.core.Formats._

/**
 * Created by famo on 1/27/15.
 */

sealed abstract class RegularType(val id: String, val label: String) extends Enum[RegularType](id)

object RegularType extends EnumCompanion[RegularType] {

  case object OneDay extends RegularType("oneDay", "one day")

  case object EveryDay extends RegularType("everyDay", "every day")

  register(
    OneDay,
    EveryDay
  )
}

case class Calender(
                     startTime: Long,
                     duration: Long,
                     regularType: RegularType = RegularType.OneDay,
                     createTime: Long,
                     updateTime: Long
                     )

object Calender {
  implicit val reminderJsonFormat = jsonFormat5(apply)
}

case class Location(name: String,
                    gps: String,
                    cityId: String,
                    createTime: Long,
                    updateTime: Long)

object Location {
  implicit val reminderJsonFormat = jsonFormat5(apply)
}

case class Meeting(id: String,
                   userId: String,
                   subject: String,
                   description: String,
                   targetUser: String,
                   price: Double,
                   calender: Calender,
                   location: Location,
                   createTime: Long,
                   updateTime: Long) extends Model[String]

object Meeting {
  implicit val reminderJsonFormat = jsonFormat10(apply)
}