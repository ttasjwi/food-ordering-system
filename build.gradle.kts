import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23" apply false
	id("org.springframework.boot") version "3.2.4" apply false
	id("io.spring.dependency-management") version "1.1.4" apply false
}

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

allprojects {
	group = "com.food.ordering.system"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply {
		plugin("org.jetbrains.kotlin.jvm")
	}

	apply {
		plugin("org.jetbrains.kotlin.plugin.spring")
	}

	apply {
		plugin("org.springframework.boot")
	}

	apply {
		plugin("io.spring.dependency-management")
	}

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
	}

	tasks.getByName("bootJar") {
		enabled = false
	}

	tasks.getByName("jar") {
		enabled = true
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "21"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

}

tasks.named<Jar>("jar") {
	enabled = false
}
