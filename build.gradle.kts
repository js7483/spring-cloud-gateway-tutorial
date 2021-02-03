
plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
}

java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

allprojects {
	group = "com.example"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
		maven { url = uri("https://repo.spring.io/milestone") }
	}
}

subprojects {

	apply(plugin = "kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	extra["springCloudVersion"] = "2020.0.0"

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		developmentOnly("org.springframework.boot:spring-boot-devtools")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		implementation("org.springframework.boot:spring-boot-starter-actuator")
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		}
	}

	tasks {
		compileKotlin {
			kotlinOptions {
				freeCompilerArgs = listOf("-Xjsr305=strict")
				jvmTarget = "11"
			}
			dependsOn(processResources)
		}

		compileTestKotlin {
			kotlinOptions {
				freeCompilerArgs = listOf("-Xjsr305=strict")
				jvmTarget = "11"
			}
		}

		test {
			useJUnitPlatform()
		}
	}
}

project(":gateway-service") {

	dependencies {
		implementation("org.springframework.cloud:spring-cloud-starter-gateway")
		implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	}
}

project(":eureka-service") {

	dependencies {
		implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
	}
}

project(":user-service") {
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	}
}

project(":shop-service") {
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	}
}



