import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
        id("com.github.davidmc24.gradle.plugin.avro-base") version "1.9.1"
}

repositories {
    gradlePluginPortal()
}

val avroSourcePath = "src/main/resources/avro"
val avroBuildPath = "build/generated/source/avro/main"

tasks.register<GenerateAvroJavaTask>("generateAvroJava") {
    source(avroSourcePath)
    setOutputDir(file(avroBuildPath))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "21"
    }

    dependsOn("generateAvroJava") // So avro is generated
}

sourceSets {
    getByName("main").java.srcDirs(avroBuildPath)
}
