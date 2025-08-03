import config.ConfigLoader
import config.ConfigLoader.Kafka
import org.apache.kafka.streams.KafkaStreams
import stream.DrivingStream
import org.apache.kafka.streams.scala.StreamsBuilder

object Main {
  def main(args: Array[String]): Unit = {
    val builder = new StreamsBuilder()
    DrivingStream.buildStream(builder)

    println(s"Loaded Kafka.bootstrapServers: ${Kafka.bootstrapServers}")


    val streams = new KafkaStreams(builder.build(), DrivingStream.createProperties())

    sys.addShutdownHook {
      streams.close()
    }

    streams.start()
    println(s"✅ Kafka Streams started with appId = ${ConfigLoader.Kafka.applicationId}")
  }
}
