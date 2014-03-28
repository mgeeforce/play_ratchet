name := "play_ratchet"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" % "jquery" % "2.1.0-2",
  "org.webjars" %% "webjars-play" % "2.2.1-2",
  "mysql" % "mysql-connector-java" % "5.1.28"
)     

play.Project.playJavaSettings
