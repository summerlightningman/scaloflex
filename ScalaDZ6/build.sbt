name := "ScalaDZ6"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"

libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.1" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")