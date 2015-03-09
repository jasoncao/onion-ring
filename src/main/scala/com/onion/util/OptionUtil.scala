package com.onion.util

/**
 * Created by famo on 3/7/15.
 */
object OptionUtil {
  implicit def option[T](t: T): Option[T] = Option(t)
  implicit def revert[T](t: Option[T]): T = t.get // WARN: This may throw None exception !!!
}
