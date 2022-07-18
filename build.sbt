// SparkSQL can work with a Spark built with Scala 2.11 too.

scalaVersion := "2.12.10"

version := "1.0.0"
val sparkVersion = "3.2.1"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "com.thesamet.scalapb" %% "sparksql32-scalapb0_11" % "1.0.0",
  "org.scalatest" %% "scalatest" % "3.0.8"
)

// Hadoop contains an old protobuf runtime that is not binary compatible
// with 3.0.0.  We shaded ours to prevent runtime issues.
assembly / assemblyShadeRules := Seq(
  ShadeRule.rename("com.google.protobuf.**" -> "shadeproto.@1").inAll,
  ShadeRule.rename("scala.collection.compat.**" -> "scalacompat.@1").inAll,
  ShadeRule.rename("shapeless.**" -> "shadeshapeless.@1").inAll
)

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value
)

