import sbt._
import Keys._

object ApplicationBuild extends Build {
  val Organization     = "com.gneoxsolutions"
  val OrganizationName = "Gneox Solutions, LLC."
  val Version          = "1.0-SNAPSHOT"
  val ScalaVersion     = "2.9.1"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := Organization,
    version      := Version,
    scalaVersion := ScalaVersion,
    crossPaths   := false,
    organizationName := OrganizationName,
    organizationHomepage := None 
  )

  val main = Project(
    id = "proxy",
    base = file(""),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= BetamaxDependencies.dependencies
    )
  )
}

object BetamaxDependencies {
  import Dependency._

  val dependencies = Seq(
    asyncHttpClient,
    betamax,
    groovy,
    specs2
  )
}

object Dependency {
  // Versions
  object V {
    val AsyncHttpClient = "1.7.6"
    val Betamax         = "1.1.2"
    val Groovy          = "2.0.6"
    val Specs2          = "1.9"
  }

  val asyncHttpClient   = "com.ning"             % "async-http-client"  % V.AsyncHttpClient
  val betamax           = "co.freeside"          % "betamax"            % V.Betamax
  val groovy            = "org.codehaus.groovy"  % "groovy"             % V.Groovy
  val specs2            = "org.specs2"          %% "specs2"             % V.Specs2     % "test"
}
