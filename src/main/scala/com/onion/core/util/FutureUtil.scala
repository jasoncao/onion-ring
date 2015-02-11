package com.onion.core.util

import akka.actor.Status.Success

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by famo on 2/6/15.
 */

object FutureUtil {
  implicit def solveFutureIterable[S](futureIterable: Future[Iterable[S]]) = new FutureIterableUtil[S](futureIterable)

  implicit def solveFutureOption[S](futureOption: Future[Option[S]]) = new FutureOptionUtil[S](futureOption)

  implicit def solveIterableFuture[S](iterableFuture: Iterable[Future[S]]) = new IterableFutureUtil[S](iterableFuture)
}

class IterableFutureUtil[S](val future: Iterable[Future[S]]) {
  def make[T](f: Iterable[S] => T): Future[T] = {
    Future.sequence(future).map(f)
  }
}

class FutureIterableUtil[S](val future: Future[Iterable[S]]) {
  def scanIterable[T](f: (S) => Future[T]): Future[Iterable[T]] = {
    future.flatMap { (iter: Iterable[S]) =>
      val target: Iterable[Future[T]] = iter.map(f)
      Future.sequence(target)
    }
  }
}

class FutureOptionUtil[S](val future: Future[Option[S]]) {
  def make[T](f: S => T): Future[Option[T]] = {
    future.map {
      case None    => None
      case Some(s) => Option(f(s))
    }
  }

  def then[T](f: S => Future[T]): Future[Option[T]] = {
    future.flatMap {
      case None    => Future(None)
      case Some(s) => f(s).map(Option(_))
    }
  }

  def get[T >: S](default: T): Future[T] = {
    future.map[T](_.getOrElse[T](default))
  }
}
