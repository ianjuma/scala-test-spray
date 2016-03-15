name := "spray-sample"

organization := "com.mlh"

version := "0.1.0-SNAPSHOT"

startYear := Some(2015)

resolvers ++= Seq(
  "RoundHeights"  at "http://maven.spikemark.net/roundeights",
  "Artima Maven Repository" at "http://repo.artima.com/releases",
  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

/* scala versions and options */
scalaVersion := "2.10.4"

val akkaVersion = "2.3.10"

val sprayVersion = "1.3.3"

// These options will be used for *all* versions.

scalacOptions ++= Seq(
  "-deprecation"
  ,"-unchecked"
  ,"-encoding", "UTF-8"
  ,"-target:jvm-1.7"
  // "-optimise"   // this option will slow your build
)

scalacOptions ++= Seq(
  "-Yclosure-elim",
  "-Yinline"
)

mainClass in assembly := Some("com.json.fix.Main")

mainClass in (Compile,run) := Some("com.json.fix.Main")

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* dependencies */
libraryDependencies ++= Seq (
  "com.typesafe.akka"       %% "akka-actor"       % akkaVersion,
  "com.typesafe.akka"       %% "akka-slf4j"       % akkaVersion,
  "io.spray"                %% "spray-json"       % "1.3.1",
  "io.spray"                %%  "spray-client"    % sprayVersion,
  "io.spray"                %%  "spray-httpx"     % sprayVersion,
  "io.spray"                %% "spray-routing"    % sprayVersion,
  "com.github.nscala-time"  %%  "nscala-time"     % "1.0.0",
  "commons-daemon"          %   "commons-daemon"  % "1.0.15",
  "io.spray"                %% "spray-can"        % sprayVersion
)
