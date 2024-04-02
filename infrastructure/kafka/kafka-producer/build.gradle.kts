dependencies {
    implementation(project(":infrastructure:kafka:kafka-model"))
    implementation(project(":infrastructure:kafka:kafka-config-data"))
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":common:common-domain"))
    implementation(project(":common:common-support:common-support-logging"))

    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.confluent:kafka-avro-serializer")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
}
