package com.onion.core.models

import scala.concurrent.{ ExecutionContext, Future }

trait UniqueSelector[M <: Model[ID], ID] {
  def id: ID
}

trait DAO[M <: Model[ID], ID] {

  /** An object used for selecting a unique record */
  type Selector <: UniqueSelector[M, ID]

  implicit def generateSelector(id: ID): Selector

  final def add(m: M)(implicit ec: ExecutionContext): Future[M] =
    for {
      added <- addImpl(prePersist(m))
      postFetched <- postFetch(added)
    } yield {
      postPersist(postFetched)
    }

  final def update(m: M)(implicit ec: ExecutionContext): Future[M] =
    for {
      updated <- updateImpl(prePersist(m))
      postFetched <- postFetch(updated)
    } yield {
      postPersist(postFetched)
    }

  def remove(selector: Selector)(implicit ec: ExecutionContext)

  def removeById(id: ID)(implicit ec: ExecutionContext) = remove(generateSelector(id))

  protected def allImpl(implicit ec: ExecutionContext): Future[Iterable[M]]

  final def all(implicit ec: ExecutionContext): Future[Iterable[M]] = fetchMany(allImpl)

  protected def findBySelector(selector: Selector)(implicit ec: ExecutionContext): Future[Option[M]]

  def findById(id: ID)(implicit ec: ExecutionContext): Future[Option[M]] = fetchOne(findBySelector(generateSelector(id)))

  /**
   * Peforms post-fetch processing on returned result
   */
  protected def fetchOne(f: => Future[Option[M]])(implicit ec: ExecutionContext): Future[Option[M]] = f flatMap {
    case Some(m) => postFetch(m) map { Option(_) }
    case None    => Future.successful(None)
  }

  /**
   * Peforms post-fetch processing on returned results
   */
  protected def fetchMany(f: => Future[Iterable[M]])(implicit ec: ExecutionContext): Future[Iterable[M]] = f flatMap { ms =>
    Future.traverse(ms)(postFetch)
  }

  protected def addImpl(m: M)(implicit ec: ExecutionContext): Future[M]
  protected def updateImpl(m: M)(implicit ec: ExecutionContext): Future[M]

  protected def prePersist(m: M)(implicit ec: ExecutionContext): M = m
  protected def postPersist(m: M)(implicit ec: ExecutionContext): M = m

  protected def postFetch(m: M)(implicit ec: ExecutionContext): Future[M] = Future.successful(m)
}

trait MutableListDAO[M <: Model[ID], ID] extends DAO[M, ID] {

  case class MSelector(id: ID) extends UniqueSelector[M, ID]

  type Selector = MSelector

  override def generateSelector(id: ID) = MSelector(id)

  protected val _all = scala.collection.mutable.ListBuffer[M]()

  override protected def allImpl(implicit ec: ExecutionContext) = Future.successful(_all.toIterable)

  override protected def addImpl(m: M)(implicit ec: ExecutionContext) = Future.successful {
    _all += m
    m
  }

  def removeAll() = _all.clear()

  override def remove(selector: Selector)(implicit ec: ExecutionContext) {
    findBySelector(selector).onSuccess {
      case Some(found) => _all -= found
      case None        => // do nothing
    }
  }

  override protected def updateImpl(m: M)(implicit ec: ExecutionContext) = Future.successful {
    removeById(m.id)
    _all += m
    m
  }

  override def findBySelector(selector: Selector)(implicit ec: ExecutionContext) = Future.successful {
    _all.find(_.id == selector.id)
  }
}
