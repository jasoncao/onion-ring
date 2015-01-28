package com.onion.models

import com.onion.core.models.Model
import com.onion.core.util.enum._
import com.onion.core.Formats._

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

case class User(id: String,
                cityId: String,
                name: String,
                password: String,
                phone: String,
                qq: String,
                email: String,
                photo: String,
                gender: Gender = Gender.Male,
                jobTitle: String,
                description: String,
                score: Int,
                createTime: Long,
                updateTime: Long) extends Model[String]

object User {
  implicit val reminderJsonFormat = jsonFormat14(apply)
}
