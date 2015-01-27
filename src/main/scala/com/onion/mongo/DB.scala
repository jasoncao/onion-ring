package com.onion.mongo

import scala.concurrent.ExecutionContext.Implicits.global
/**
 * Created by famo on 1/27/15.
 */
object DB extends ReactiveMongoPersistence{

  import reactivemongo.api._

  val driver = new MongoDriver
  lazy val connection = driver.connection(List("localhost"))
  lazy val db = connection("onion")

  implicit object JsonTypeMapper extends SprayJsonTypeMapper with NormalizedIdTransformer


}
