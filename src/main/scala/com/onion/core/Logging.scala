package com.onion.core

trait Logging {

  def loggerName = "app"

  import org.slf4j.LoggerFactory

  lazy val Logger = LoggerFactory.getLogger(loggerName)

}

