package com.onion.mongo

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

import com.onion.core.models.{ UniqueSelector, Model }
import com.onion.model.{ User, Meeting }
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import spray.json.RootJsonFormat
import com.onion.core.Formats._
/**
 * Created by famo on 1/27/15.
 */
object DB extends ReactiveMongoPersistence {

  import reactivemongo.api._

  val driver = new MongoDriver
  lazy val connection = driver.connection(List("localhost"))
  lazy val db = connection("onion")

  implicit object JsonTypeMapper extends SprayJsonTypeMapper with NormalizedIdTransformer

  abstract class UnsecuredDAO[M <: Model[String]](collName: String)(withNewId: M => M)(implicit jsformat: RootJsonFormat[M]) extends CollectionDAO[M, String](db(collName)) {

    case class Selector(id: String) extends UniqueSelector[M, String]

    override def generateSelector(id: String) = Selector(id)

    override protected def addImpl(m: M)(implicit ec: ExecutionContext) = doAdd(withNewId(m))

    override protected def updateImpl(m: M)(implicit ec: ExecutionContext) = doUpdate(m)

    override def remove(selector: Selector)(implicit ec: ExecutionContext) = uncheckedRemoveById(selector.id)
  }

  //  def newGuid = java.util.UUID.randomUUID.toString

  private val seqIdMap = new ConcurrentHashMap[String, AtomicLong]

  def sequenceId(`type`: String) = {
    seqIdMap.putIfAbsent(`type`, new AtomicLong(0))
    seqIdMap.get(`type`).getAndIncrement
  }

  object MeetingDao extends UnsecuredDAO[Meeting]("meeting")(_.copy(id = sequenceId("meeting").toString))

  object UserDao extends UnsecuredDAO[User]("user")(_.copy(id = sequenceId("user").toString))
}
