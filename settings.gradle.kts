rootProject.name = "food-ordering-system"
include(
    "order-service:order-domain:order-domain-core",
    "order-service:order-domain:order-application-service",
    "order-service:order-application",
    "order-service:order-data-access",
    "order-service:order-messaging",
    "order-service:order-container"
)
