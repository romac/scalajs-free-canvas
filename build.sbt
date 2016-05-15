
enablePlugins(ScalaJSPlugin)

name := "free-canvas"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies += "org.typelevel" %%% "cats"        % "0.4.1"
libraryDependencies += "org.scala-js"  %%% "scalajs-dom" % "0.9.0"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  // "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import"
)

