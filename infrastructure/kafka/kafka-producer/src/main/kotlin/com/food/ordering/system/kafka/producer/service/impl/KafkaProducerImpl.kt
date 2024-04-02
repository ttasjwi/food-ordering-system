package com.food.ordering.system.kafka.producer.service.impl

import com.food.ordering.system.kafka.producer.exception.KafkaProducerException
import com.food.ordering.system.kafka.producer.service.KafkaProducer
import com.food.ordering.system.support.logging.getLogger
import jakarta.annotation.PreDestroy
import org.apache.avro.specific.SpecificRecordBase
import org.springframework.kafka.KafkaException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.function.BiConsumer

@Component
class KafkaProducerImpl<K: Serializable, V: SpecificRecordBase>(

    private val kafkaTemplate: KafkaTemplate<K, V>,

) : KafkaProducer<K,V> {

    private val log = getLogger(javaClass)

    override fun send(topicName: String, key: K, message: V, callback: BiConsumer<SendResult<K, V>, Throwable>) {
        log.info { "Sending message = $message to topic=$topicName"  }
        try {
            val kafkaResultFuture = kafkaTemplate.send(topicName, key, message)
            kafkaResultFuture.whenComplete(callback)
        } catch (e: KafkaException) {
            log.error(e) {"Error on kafka producer with key: $key, message: $message and exception: ${e.message}" }
            throw KafkaProducerException("Error on kafka producer with key: $key, message: $message and exception: ${e.message}")
        }
    }

    @PreDestroy
    fun close() {
        log.info { "Closing kafka producer" }
        kafkaTemplate.destroy()
    }
}
