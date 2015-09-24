package com.onion.model

import sprest.Formats._
import sprest.models.Model

/**
 * Created by famo on 3/16/15.
 */
case class Booking(
  id:          String,
  subject:     Option[String],
  target:      Option[String],
  desc:        Option[String],
  price:       Option[Double],
  sellerId:    Option[String],
  buyerId:     Option[String],
  meetingId:   Option[String],
  time:        Option[Long],
  duration:    Option[Long],
  address:     Option[String],
  gps:         Option[String],
  createTime:  Option[Long],
  updateTime:  Option[Long]
) extends Model[String]

object Booking {
  implicit val reminderJsonFormat = jsonFormat14(apply)
}
