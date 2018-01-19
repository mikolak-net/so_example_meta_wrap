name := "testPluginWithTypetags"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.4"
)

lazy val compilerPlugin = project
  .in(file("compiler_plugin"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang"    % "scala-compiler" % scalaVersion.value,
      "org.scala-lang"    % "scala-reflect"  % scalaVersion.value),
    exportJars := true
  ).dependsOn(root)

lazy val compilerPluginTest = project
  .in(file("compiler_plugin_test"))
  .settings(commonSettings)
  .dependsOn(compilerPlugin)
  .settings(
    scalacOptions += (artifactPath in (compilerPlugin, Compile, packageBin)).map { file =>
      s"-Xplugin:${file.getAbsolutePath}"}.value,
    scalacOptions += "-Xlog-implicits",
    libraryDependencies += "org.scala-lang"    % "scala-reflect"  % scalaVersion.value
  )

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(libraryDependencies += "org.scala-lang"    % "scala-reflect"  % scalaVersion.value)
