import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
	id("com.palantir.docker") version "0.25.0"
	id("com.palantir.docker-compose") version "0.25.0"
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
	apply(plugin = "com.palantir.docker")
	apply(plugin = "com.palantir.docker-compose")

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

		dockerComposeDown { enabled = false }
		dockerComposeUp { enabled = false}
	}

	docker {
		val bootJar: BootJar by tasks

		name = project.name
		files(".container/Dockerfile", "build/libs/${bootJar.archiveFileName.get()}")
		buildArgs(mapOf("JAR_FILE" to bootJar.archiveFileName.get()))
	}
}

project(":gateway-service") {

	dependencies {
		implementation("org.springframework.cloud:spring-cloud-starter-gateway")
		implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
		implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
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

tasks.bootJar {enabled = false}
tasks.jar {enabled = true}
tasks.bootRun {enabled = false}
tasks.docker {enabled = false}

dockerCompose {
	setDockerComposeFile("docker-compose.yml")
}