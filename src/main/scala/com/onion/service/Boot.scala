package com.onion.service

import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
import akka.io.IO
import spray.can.Http
import akka.pattern.ask

/**
 * Created by famo on 3/7/15.
 */
object Boot extends App{
  implicit val system = ActorSystem("onion-system")
  val service = system.actorOf(Props[MyServiceActor],"onion-service")
  implicit val timeout = Timeout(5 seconds)
  IO(Http) ? Http.Bind(service, interface = "127.0.0.1",port = 8080)
}
