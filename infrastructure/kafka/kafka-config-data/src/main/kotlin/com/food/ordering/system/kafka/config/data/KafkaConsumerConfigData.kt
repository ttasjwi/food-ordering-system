package com.food.ordering.system.kafka.config.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
data class KafkaConsumerConfigData (
    private val keyDeserializer: String? = null,
    private val valueDeserializer: String? = null,
    private val autoOffsetReset: String? = null,
    private val specificAvroReaderKey: String? = null,
    private val specificAvroReader: String? = null,
    private val batchListener: Boolean? = null,
    private val autoStartup: Boolean? = null,
    private val concurrencyLevel: Int? = null,
    private val sessionTimeoutMs: Int? = null,
    private val heartbeatIntervalMs: Int? = null,
    private val maxPollIntervalMs: Int? = null,
    private val pollTimeoutMs: Long? = null,
    private val maxPollRecords: Int? = null,
    private val maxPartitionFetchBytesDefault: Int? = null,
    private val maxPartitionFetchBytesBoostFactor: Int? = null
)
