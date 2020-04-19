val catsVersion           = "2.1.0"
val catsEffectVersion     = "2.1.2"
val fs2Version            = "2.2.1"
val scodecCoreVersion     = "1.11.6"
val scodecCatsVersion     = "1.0.0"
val specs2Version         = "4.9.2"
val logbackVersion        = "1.2.3"
val tikaVersion           = "1.23"
val scalacheckVersion     = "1.14.1"
val scodecStreamVersion   = "2.0.0"
val catsEffectTestVersion = "0.3.0"

lazy val root = (project in file("."))
  .settings(
    organization := "com.minosiants",
    name := "pencil",
    scalaVersion := "2.12.11",
    crossScalaVersions := Seq("2.12.11", "2.13.1"),
    scalacOptions ++= Seq(
      "-language:experimental.macros",
      "-Yrangepos",
      "-Ywarn-unused",
      "-Xlint"
    ),
    javacOptions ++= Seq("-source", "1.11", "-target", "1.8"),
    libraryDependencies ++= Seq(
      "org.typelevel"   %% "cats-core"                  % catsVersion,
      "org.typelevel"   %% "cats-effect"                % catsEffectVersion,
      "co.fs2"          %% "fs2-core"                   % fs2Version,
      "co.fs2"          %% "fs2-io"                     % fs2Version,
      "org.scodec"      %% "scodec-core"                % scodecCoreVersion,
      "org.scodec"      %% "scodec-cats"                % scodecCatsVersion,
      "org.apache.tika" % "tika-core"                   % tikaVersion,
      "org.scala-lang"  % "scala-reflect"               % scalaVersion.value,
      "org.specs2"      %% "specs2-core"                % specs2Version % "test",
      "org.specs2"      %% "specs2-scalacheck"          % specs2Version % Test,
      "org.scalacheck"  %% "scalacheck"                 % scalacheckVersion % "test",
      "com.codecommit"  %% "cats-effect-testing-specs2" % catsEffectTestVersion % "test",
      "org.scodec"      %% "scodec-stream"              % scodecStreamVersion % "test",
      "ch.qos.logback"  % "logback-classic"             % logbackVersion
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
    addCompilerPlugin(scalafixSemanticdb)
  )
  .settings(releaseProcessSettings)
  .settings(licenceSettings)

import ReleaseTransformations._
lazy val releaseProcessSettings = Seq(
  releaseIgnoreUntrackedFiles := true,
  releaseCrossBuild := true,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)

lazy val licenceSettings = Seq(
  organizationName := "Kaspar Minosiants",
  startYear := Some(2020),
  licenses += ("Apache-2.0", new URL(
    "https://www.apache.org/licenses/LICENSE-2.0.txt"
  ))
)
