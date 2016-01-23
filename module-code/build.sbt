import play.sbt.PlayImport.PlayKeys._

name := "SecureSocial"

version := Common.version

scalaVersion := Common.scalaVersion

//PlayKeys.generateRefReverseRouter := false

libraryDependencies ++= Seq(
  cache,
  ws,
  filters,
  specs2 % "test",
  "com.typesafe.play" %% "play-mailer" % "3.0.1",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

scalariformSettings

resolvers ++= Seq(
  Resolver.typesafeRepo("releases")
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

organization := "ws.securesocial"

organizationName := "SecureSocial"

organizationHomepage := Some(new URL("http://www.securesocial.ws"))
