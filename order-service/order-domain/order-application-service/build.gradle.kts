dependencies {

    implementation(project(":common:common-domain"))
    implementation(project(":common:common-support:common-support-logging"))
    implementation(project(":order-service:order-domain:order-domain-core"))

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework:spring-tx")
}
