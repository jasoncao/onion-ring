package onionring.service

import akka.actor.{Props, Actor}
import akka.pattern.ask
import onionring.dao.{Get, MeetingDao}
import onionring.pojo.Listing

/**
* Created by famo on 1/25/15.
*/
class MeetingService extends Actor{
  lazy val meetingDao = context.system.actorOf(Props(new MeetingDao))

  override def receive = {
    case GetMeetingList(cityId: Long, offset:Long, limit: Long) => {
      // TODO query by city id
//      val meeting = meetingDao ? Get(offset,limit)
    }
  }
}

case class GetMeetingList(cityId: Long, offset:Long, limit: Long)
