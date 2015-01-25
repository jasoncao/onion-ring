package onionring.dao

import akka.actor.{Props, ActorSystem, Actor}
import akka.util.Timeout
import com.mongodb.casbah.commons.MongoDBObject
import onionring.pojo.User
import akka.pattern.ask
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by famo on 1/25/15.
 */
class UserDao extends Actor {

  lazy val userDB = MongoPersistent.onionRingDB("User")

  override def receive = {
    case GetUser(userId: Long) => val result = userDB.find(MongoDBObject("userId" -> userId))
      sender ! result.map(record => {
        User(userId,
          record.get("cityName").asInstanceOf[String],
          record.get("name").asInstanceOf[String],
          record.get("phone").asInstanceOf[String],
          record.get("qq").asInstanceOf[String],
          record.get("email").asInstanceOf[String],
          record.get("photo").asInstanceOf[String],
          record.get("gender").asInstanceOf[String],
          record.get("jobTitle").asInstanceOf[String],
          record.get("description").asInstanceOf[String],
          record.get("score").asInstanceOf[Int],
          record.get("createTime").asInstanceOf[String],
          record.get("updateTime").asInstanceOf[String]
        )
      }).toList.apply(0)
    case SaveUser(user: User) => val userObject = MongoDBObject(
      "userId" -> user.userId,
      "cityName" -> user.cityName,
      "phone" -> user.phone,
      "qq" -> user.qq,
      "email" -> user.email,
      "photo" -> user.photo,
      "gender" -> user.gender,
      "jobTitle" -> user.jobTitle,
      "description" -> user.description,
      "score" -> user.score,
      "createTime" -> user.createTime,
      "updateTime" -> user.updateTime
    )
      userDB.insert(userObject)
  }

}

case class SaveUser(user: User)

case class GetUser(userId: Long)

object Test2 extends App {
  implicit val timeout = Timeout(100.days)
  val system = ActorSystem("test")
  val userDao = system.actorOf(Props(new UserDao))
  val result = userDao ? GetUser(-1)

  println(Await.result(result, 100 days))
  //  val user = User(-1,"haha","hahaha","123456","123","123","123","g","gg","dee",1,"now","now")
  //  userDao ! SaveUser(user)

}