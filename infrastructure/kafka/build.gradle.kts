subprojects {
    repositories {
        maven{
            url = uri("https://packages.confluent.io/maven/")
        }
    }

    dependencies {
        implementation("org.springframework.kafka:spring-kafka")
        implementation("io.confluent:kafka-avro-serializer:7.6.0")
        implementation("org.apache.avro:avro:1.11.3")
    }
}

