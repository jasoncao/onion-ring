package com.onion.model

import sprest.models.Model
import sprest.util.enum.EnumCompanion
import sprest.Formats._
import sprest.util.enum.Enum
/**
* Created by famo on 1/28/15.
*/

sealed abstract class Gender(val id: String, val label: String) extends Enum[Gender](id)

object Gender extends EnumCompanion[Gender] {

  case object Male extends Gender("male", "Male")

  case object Female extends Gender("female", "Female")

  register(
    Male,
    Female
  )
}

case class User(
  id:          String,
  cityId:      String,
  name:        String,
  password:    String,
  phone:       String,
  qq:          String,
  email:       String,
  photo:       String,
  gender:      Gender  = Gender.Male,
  jobTitle:    String,
  description: String,
  score:       Int,
  createTime:  Long,
  updateTime:  Long,
  isDeleted:   Boolean
) extends Model[String]

object User {
  implicit val reminderJsonFormat = jsonFormat15(apply)
}
