rootProject.name = "food-ordering-system"
include(
    "common:common-domain",
    "common:common-application",
    "common:common-data-access",
    "common:common-support:common-support-logging",
    "order-service:order-domain:order-domain-core",
    "order-service:order-domain:order-application-service",
    "order-service:order-application",
    "order-service:order-data-access",
    "order-service:order-messaging",
    "order-service:order-container"
)
