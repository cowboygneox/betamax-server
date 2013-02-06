import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

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
    settings = buildSettings ++ assemblySettings ++ Seq(
      libraryDependencies ++= BetamaxDependencies.dependencies
    )
  )
}

object BetamaxDependencies {
  import Dependency._

  val dependencies = Seq(
    groovy,
    slf4j,
    specs2,
    commonsLang,
    jetty,
    httpclient,
    snakeyaml,
    junit
  )
}

object Dependency {
  // Versions
  object V {
    val Groovy          = "1.8.8"
    val Slf4j           = "1.7.2"
    val Specs2          = "1.9"
  }

  val slf4j             = "org.slf4j"                  % "slf4j-simple" % V.Slf4j
  val specs2            = "org.specs2"                %% "specs2"       % V.Specs2      % "test"

  // betamax dependencies
  val groovy            = "org.codehaus.groovy"        % "groovy-all"   % V.Groovy
  val commonsLang       = "commons-lang"               % "commons-lang" % "2.4"
  val jetty             = "org.eclipse.jetty"          % "jetty-server" % "7.3.1.v20110307"
  val httpclient        = "org.apache.httpcomponents"  % "httpclient"   % "4.2.1"
  val snakeyaml         = "org.yaml"                   % "snakeyaml"    % "1.10"
  val junit             = "junit"                      % "junit"        % "4.8.2"
}
