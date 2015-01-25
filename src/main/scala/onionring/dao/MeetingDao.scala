package onionring.dao

import akka.actor.{Props, ActorSystem, Actor}
import com.mongodb.BasicDBObject
import com.mongodb.casbah.commons.MongoDBObject
import onionring.pojo.{Calender, Location, Listing}
import akka.pattern.ask
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by famo on 1/25/15.
 */
case class Save(data: Listing)

case class Get(offset: Long, limit: Long)

class MeetingDao extends Actor {
  lazy val meeting = MongoPersistent.onionRingDB("Meeting")

  override def receive = {
    case Save(data: Listing) =>
      val location = MongoDBObject(
        "locationName" -> data.location.locationName,
        "locationGPS" -> data.location.locationGPS,
        "createTime" -> data.location.createTime,
        "updateTime" -> data.location.updateTime
      )

      val calender = MongoDBObject(
        "startTime" -> data.calender.startTime,
        "duration" -> data.calender.duration,
        "regularType" -> data.calender.regularType,
        "createTime" -> data.calender.createTime,
        "updateTime" -> data.calender.updateTime
      )

      val listing = MongoDBObject(
        "listingId" -> data.listingId,
        "userId" -> data.userId,
        "subject" -> data.subject,
        "targetUser" -> data.targetUser,
        "money" -> data.money,
        "calender" -> calender,
        "location" -> location,
        "createTime" -> data.createTime,
        "updateTime" -> data.updateTime
      )
      meeting.insert(listing)
    case Get(offset: Long, limit: Long) => val result = meeting.find().skip(offset.toInt).limit(limit.toInt)
      sender ! result.map(record => {
        val location = record.get("location").asInstanceOf[BasicDBObject]
        val calender = record.get("calender").asInstanceOf[BasicDBObject]
        Listing(record.get("listingId").asInstanceOf[Long],
          record.get("userId").asInstanceOf[Long],
          record.get("subject").asInstanceOf[String],
          record.get("targetUser").asInstanceOf[String],
          record.get("money").asInstanceOf[Double],
          Calender(calender.get("startTime").asInstanceOf[Long],
            calender.get("duration").asInstanceOf[Long],
            calender.get("regularType").asInstanceOf[String],
            calender.get("createTime").asInstanceOf[String],
            calender.get("updateTime").asInstanceOf[String]),
          Location(location.get("locationName").asInstanceOf[String],
            location.get("locationGPS").asInstanceOf[String],
            location.get("createTime").asInstanceOf[String],
            location.get("updateTime").asInstanceOf[String]),
          record.get("createTime").asInstanceOf[String],
          record.get("updateTime").asInstanceOf[String])
      }).toList
  }
}

object Test extends App {
  val system = ActorSystem("test")
  val meetingDao = system.actorOf(Props(new MeetingDao))

  //  val location = Location("haha","hahaGPS","lala","lala")
  //  val calender = Calender(-1,0,"hahatype","lala","lala")
  //  val listing = Listing(-1,-1,"hahasubject","hahaterget",0.01,calender,location,"lala","lala")
  //  meetingDao ! Save(listing)
  implicit val timeout = Timeout(100.days)

  val result = meetingDao ? Get(0, 10)
//  result.onSuccess {
//    case x => println(x)
//  }
  val res = Await.result(result, 100.days)

  println(res)
  system.shutdown()

}
