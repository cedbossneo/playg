import play.Project._

name := "playg"

version := "1.0"

playScalaSettings

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2"
)