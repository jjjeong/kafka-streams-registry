package config

import com.typesafe.config.{Config, ConfigFactory}

object ConfigLoader {
  private val config: Config = ConfigFactory.load()

  object Kafka {
    val bootstrapServers: String = config.getString("kafka.bootstrap.servers")
    val schemaRegistryUrl: String = config.getString("kafka.schema.registry.url")
    val inputTopic: String = config.getString("kafka.input.topic")
    val outputTopic: String = config.getString("kafka.output.topic")
    val applicationId: String = config.getString("kafka.application.id")
  }
}

