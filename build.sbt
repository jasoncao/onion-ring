scalaVersion := "2.10.4"

resolvers += "sprest snapshots" at "http://sprest.io/releases"

resolvers += "play" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "sprest" %% "sprest-core" % "0.3.8"

libraryDependencies += "sprest" %% "sprest-reactivemongo" % "0.3.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.3"