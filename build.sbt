//ThisBuild / version := "0.1.0-SNAPSHOT"
//
//ThisBuild / scalaVersion := "2.12.8"
//
//lazy val root = (project in file("."))
//  .settings(
//    name := "kafka-streams-registry",
//    version := "0.1.0",
//    libraryDependencies ++= Seq(
//      // Kafka Streams (Scala DSL 포함)
//      "org.apache.kafka" %% "kafka-streams-scala" % "3.6.1",
//
//      "io.confluent" % "kafka-streams-avro-serde" % "7.5.1",
//
//      // Avro SerDe + Schema Registry
//      "io.confluent" % "kafka-avro-serializer" % "7.5.1",
//
//      // Logging
//      "ch.qos.logback" % "logback-classic" % "1.4.11",
//
//      // 설정파일 읽기용
//      "com.typesafe" % "config" % "1.4.3",
//
//      // Scala 로깅 wrapper
//      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
//    ),
//
//    // 필요한 리포지토리 추가
//    resolvers ++= Seq(
//      "Confluent" at "https://packages.confluent.io/maven/",
//      Resolver.mavenCentral
//    )
//  )
//
//Compile / sourceGenerators += (Compile / avroScalaGenerate).taskValue
//Compile / unmanagedSourceDirectories += baseDirectory.value / "src/main/java"

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.8"

lazy val root = (project in file("."))
  .settings(
    name := "kafka-streams-registry",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      // Kafka Streams (Scala DSL 포함)
      "org.apache.kafka" %% "kafka-streams-scala" % "3.6.1",

      // Avro SerDe + Schema Registry
      "io.confluent" % "kafka-avro-serializer" % "7.5.1",
      "io.confluent" % "kafka-streams-avro-serde" % "7.5.1",

      // Logging
      "ch.qos.logback" % "logback-classic" % "1.4.11",

      // 설정파일 읽기용
      "com.typesafe" % "config" % "1.4.3",

      // Scala 로깅 wrapper
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
    ),

    // 필요한 리포지토리 추가
    resolvers ++= Seq(
      "Confluent" at "https://packages.confluent.io/maven/",
      Resolver.mavenCentral
    ),

    // Java 소스 디렉토리 포함
    Compile / unmanagedSourceDirectories += baseDirectory.value / "src/main/java"
  )
