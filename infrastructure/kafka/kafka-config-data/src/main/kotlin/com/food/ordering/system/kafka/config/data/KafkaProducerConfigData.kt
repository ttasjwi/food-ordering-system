package com.food.ordering.system.kafka.config.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
data class KafkaProducerConfigData(
    private val keySerializerClass: String? = null,
    private val valueSerializerClass: String? = null,
    private val compressionType: String? = null,
    private val acks: String? = null,
    private val batchSize: Int? = null,
    private val batchSizeBoostFactor: Int? = null,
    private val lingerMs: Int? = null,
    private val requestTimeoutMs: Int? = null,
    private val retryCount: Int? = null
)
