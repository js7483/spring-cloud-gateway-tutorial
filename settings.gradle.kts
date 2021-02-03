pluginManagement {
	repositories {
		mavenCentral()
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
}
rootProject.name = "scg"
include("user-service")
include("shop-service")
include("gateway-service")
include("eureka-service")
