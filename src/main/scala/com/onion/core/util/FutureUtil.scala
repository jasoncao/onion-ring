package com.onion.core.util

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
  def make[T](f: S => T): Future[T] = {
    future.filter(_.isDefined).map {
      case Some(s) => f(s)
    }
  }

  def then[T](f: S => Future[T]): Future[T] = {
    future.filter(_.isDefined).flatMap {
      case Some(s) => f(s)
    }
  }
}
