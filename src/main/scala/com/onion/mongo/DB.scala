package com.onion.mongo

import com.onion.model.{User, Meeting}
import reactivemongo.bson.BSONDocument
import spray.json.{RootJsonReader, RootJsonFormat}
import sprest.models.{UniqueSelector, Model}
import sprest.reactivemongo.ReactiveMongoPersistence
import sprest.reactivemongo.typemappers.{NormalizedIdTransformer, SprayJsonTypeMapper}
import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.ExecutionContext.Implicits.global
import com.onion.util.OptionUtil._
import sprest.Formats._

/**
 * Created by famo on 3/7/15.
 */
object DB extends ReactiveMongoPersistence {

  import reactivemongo.api._

  val driver = new MongoDriver
  lazy val connection = driver.connection(List("localhost"))
  lazy val db = connection("onion")

  implicit object JsonTypeMapper extends SprayJsonTypeMapper with NormalizedIdTransformer

  abstract class UnsecuredDAO[M <: Model[String]](collName: String)(withNewId: M => M)(implicit jsformat: RootJsonFormat[M]) extends CollectionDAO[M, String](db(collName)) {
    implicit val numberPerPage: Int = 10

    case class Selector(id: String) extends UniqueSelector[M, String]

    override def generateSelector(id: String) = Selector(id)

    override protected def addImpl(m: M)(implicit ec: ExecutionContext) = doAdd(withNewId(m))

    override protected def updateImpl(m: M)(implicit ec: ExecutionContext) = doUpdate(m)

    override def remove(selector: Selector)(implicit ec: ExecutionContext) = uncheckedRemoveById(selector.id)

    def find[P](selector: BSONDocument = BSONDocument(), pageNumber: Int)(implicit numberPerPage: Int, reads: RootJsonReader[P], ec: ExecutionContext, jsformat: RootJsonFormat[P]) = {
      implicit val bsonFormat: BSONFormat[P] = generateBSONFormat[P]
      collection.find(selector).options(QueryOpts((pageNumber - 1) * numberPerPage, numberPerPage)).cursor[P].collect[List](numberPerPage)
    }
  }

  def newGuid = System.currentTimeMillis + "-" + java.util.UUID.randomUUID.toString

  def generateMeetingIds(meeting: Meeting): Meeting = {
    val _locations = meeting.locations.getOrElse(List()).map(_.copy(id = newGuid))
    val _calenders = meeting.calenders.getOrElse(List()).map(_.copy(id = newGuid))
    val _comments = meeting.comments.getOrElse(List()).map(_.copy(id = newGuid))
    val _createTime = meeting.createTime.getOrElse(System.currentTimeMillis())
    meeting.copy(id = newGuid, locations = _locations, calenders = _calenders, comments = _comments, createTime = _createTime, updateTime = System.currentTimeMillis())
  }

  object MeetingDao extends UnsecuredDAO[Meeting]("meeting")(generateMeetingIds) {

    def findByCityId(cityId: String, pageNum: Int = 1): Future[List[Meeting]] = find[Meeting](BSONDocument("cityId" -> cityId), pageNum)

    def findByPage(pageNum: Int = 1): Future[List[Meeting]] = find[Meeting](BSONDocument(), pageNum)
  }

  object UserDao extends UnsecuredDAO[User]("user")(_.copy(id = newGuid))

}