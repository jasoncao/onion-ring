package onionring.pojo

import spray.json.DefaultJsonProtocol


/**
 * Created by famo on 1/25/15.
 */

case class Location(locationName: String, locationGPS: String, createTime: String, updateTime: String)

case class Calender(startTime: Long, duration: Long, regularType: String, createTime: String, updateTime: String)

case class Listing(listingId: Long, userId: Long, subject: String, targetUser: String, money: Double, calender: Calender, location: Location, createTime: String, updateTime: String)
