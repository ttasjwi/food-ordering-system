dependencies {
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":order-service:order-domain:order-application-service"))
    implementation(project(":order-service:order-application"))
    implementation(project(":order-service:order-data-access"))
    implementation(project(":order-service:order-messaging"))
}
