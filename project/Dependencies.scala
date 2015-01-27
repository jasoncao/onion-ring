import sbt._

object Version {
  val akka = "2.3.8"
  val akkaDataReplication = "0.8"
  val akkaHttp = "1.0-M2"
  val akkaPersistenceMongo = "0.7.4"
  val akkaSse = "0.2.1"
  val logback = "1.1.2"
  val scala = "2.11.5"
  val scalaTest = "2.2.3"
  val sprayJson = "1.3.1"
  val reactiveMongo ="0.11.0-SNAPSHOT"
}

object Library {
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % Version.akka
  val akkaContrib = "com.typesafe.akka" %% "akka-contrib" % Version.akka
  val akkaDataReplication = "com.github.patriknw" %% "akka-data-replication" % Version.akkaDataReplication
  val akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % Version.akkaHttp
  val akkaPersistenceMongo = "com.github.ddevore" %% "akka-persistence-mongo-casbah" % Version.akkaPersistenceMongo
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % Version.akka
  val akkaSse = "de.heikoseeberger" %% "akka-sse" % Version.akkaSse
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % Version.akka
  val logbackClassic = "ch.qos.logback" % "logback-classic" % Version.logback
  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
  val sprayJson = "io.spray" %% "spray-json" % Version.sprayJson
  val reactiveMongo = "org.reactivemongo" %% "reactivemongo" % Version.reactiveMongo
}

object Resolver {
  val hseeberger = "hseeberger at bintray" at "http://dl.bintray.com/hseeberger/maven"
  val patriknw = "patriknw at bintray" at "http://dl.bintray.com/patriknw/maven"
  val sonatype = "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
  val typesafe = "Typesafe releases" at "http://repo.typesafe.com/typesafe/releases"
}
