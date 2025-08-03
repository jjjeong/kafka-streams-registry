package stream

import config.ConfigLoader.Kafka
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.StreamsConfig

import java.util.Properties
import org.apache.kafka.common.serialization.{Serde, Serdes}
import org.apache.kafka.streams.scala.Serdes._
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import com.onboarding.avro.DrivingData


import java.time.{Instant, ZoneId, ZonedDateTime}
import java.time.format.DateTimeFormatter
import scala.jdk.CollectionConverters._
import org.apache.kafka.streams.scala.kstream.Consumed


object DrivingStream {

  def buildStream(builder: StreamsBuilder): Unit = {
    // SerDe 설정
    val inputSerde = new SpecificAvroSerde[DrivingData]
    inputSerde.configure(Map("schema.registry.url" -> Kafka.schemaRegistryUrl).asJava, false)

    val stringSerde = Serdes.String()
//    val doubleSerde: Serde[JDouble] = Serdes.Double().asInstanceOf[Serde[JDouble]]


    // 입력 스트림 정의
    val drivingStream: KStream[String, DrivingData] =
      builder.stream[String, DrivingData](Kafka.inputTopic)(
        Consumed.`with`(stringSerde, inputSerde)
      )

    // 시간대 단위 추출 함수
    def extractHour(timestamp: Long): String = {
      val zoned = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("Asia/Seoul"))
      zoned.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00"))
    }

    // 변환 및 집계
    val aggregatedStream = drivingStream
      .map((_, value) => {
        val hour = extractHour(value.getTimestamp())
        val newKey = s"${value.getCarTypeId()}_$hour"
        (newKey, value.getDistance())
      })
      //.groupByKey(Grouped.`with`(stringSerde, Serdes.Double()))
      .groupByKey(Grouped.`with`[String, Double])
      .reduce(_ + _)(Materialized.`with`(String, Double))
    //.groupByKey(Grouped.with(stringSerde, doubleSerde))
    //.toStream.to(Kafka.outputTopic)(Produced.with(stringSerde, doubleSerde))

    // 출력 토픽으로 전송
    aggregatedStream
      .toStream
      .to(Kafka.outputTopic)(Produced.`with`(String, Double))
  }

  def createProperties(): Properties = {
    val props = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, Kafka.applicationId)
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Kafka.bootstrapServers)
    props.put("schema.registry.url", Kafka.schemaRegistryUrl)
    props
  }
}
