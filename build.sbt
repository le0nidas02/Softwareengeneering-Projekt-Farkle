name          := "farkle"
organization  := "de.htwg.se"
version       := "0.0.1"
scalaVersion  := "3.3.7" // Aktualisiert für optimalen Metals-Support!

// Aktuelles ScalaTest für Scala 3
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"

//*******************************************************************************//
// Libraries für spätere Tasks (kompatibel mit Scala 3)
// Auskommentiert lassen, bis wir sie brauchen!

// libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
// libraryDependencies += "com.google.inject" % "guice" % "5.1.0"
// libraryDependencies += "net.codingwell" %% "scala-guice" % "5.1.1"
// libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.3.0"
// libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.4"