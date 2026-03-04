plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.sentry.jvm.gradle") version "6.0.0"
	//gestion de vulnerabilité
	id("org.owasp.dependencycheck") version "12.2.0"
	kotlin("plugin.jpa") version "2.2.21"
}

group = "emy.backend"
version = "0.0.1-SNAPSHOT"
description = "LawApp50 project for Spring Boot"
sentry {
	// Generates a JVM (Java, Kotlin, etc.) source bundle and uploads your source code to Sentry.
	// This enables source context, allowing you to see your source
	// code as part of your stack traces in Sentry.
	includeSourceContext = true
	org = "casanayo"
	projectName = "lawapp50-backend"
	authToken =  System.getenv("SENTRY_AUTH_TOKEN")
}
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}
extra["springCloudGcpVersion"] = "7.3.1"
extra["springCloudVersion"] = "2025.0.0"
extra["sentryVersion"] = "8.27.0"
dependencies {
	//data-jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//data-r2bc
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	//security
	implementation("org.springframework.boot:spring-boot-starter-security")
	//thymeleaf
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	// reactive
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	// web
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	// validation
	implementation("org.springframework.boot:spring-boot-starter-validation")
	//rate limiting
	implementation("com.bucket4j:bucket4j-core:8.7.0")
	//swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	//
	implementation("io.r2dbc:r2dbc-pool:1.0.2.RELEASE")
	implementation("org.postgresql:r2dbc-postgresql:1.1.1.RELEASE")
	// flyway
	implementation("org.springframework.boot:spring-boot-starter-flyway")
	// verify phone
	implementation("com.googlecode.libphonenumber:libphonenumber:8.13.38")
	// crypto
	implementation("org.springframework.security:spring-security-crypto")
	// sentry
	implementation("io.sentry:sentry:8.31.0")
	//gcs
	implementation("com.google.cloud:spring-cloud-gcp-starter-storage")
	// reactive
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
//	implementation("org.thymeleaf.extras:thymeleaf-extras-spring-security6")
	implementation("tools.jackson.module:jackson-module-kotlin")
    //websocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.flywaydb:flyway-database-postgresql:11.19.0")
	runtimeOnly("com.ongres.scram:scram-common:3.2")
   // developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-r2dbc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-thymeleaf-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
dependencyManagement {
	imports {
		mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:${property("springCloudGcpVersion")}")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}
kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
