/*
 * Copyright 2014 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.onion.core.util

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}

import scala.collection.breakOut

object BaseApp {

  private val opt = """-D(\S+)=(\S+)""".r

  def applySystemProperties(args: Array[String]): Unit = {
    def argsToProps(args: Array[String]) =
      args.collect { case opt(key, value) => key -> value }(breakOut)
    for ((key, value) <- argsToProps(args))
      System.setProperty(key, value)
  }
}

abstract class BaseApp[A] {
  import  BaseApp._
  def main(args: Array[String]) {
    applySystemProperties(args)
    val system = ActorSystem("onion-ring")
    val log = Logging.apply(system, getClass)
    run(system, log)
    system.awaitTermination()
  }

  def run(system: ActorSystem, log: LoggingAdapter): Unit
}
