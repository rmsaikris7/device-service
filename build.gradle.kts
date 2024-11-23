plugins {
	java
	id("org.springframework.boot") version libs.versions.springBoot
	id("io.spring.dependency-management") version libs.versions.dependencyManagement
	id("org.flywaydb.flyway") version libs.versions.flyway
	id("com.diffplug.spotless") version libs.versions.spotless
}

group = "com.myproject.device"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.springBootStarterDataJpa)
	implementation(libs.springBootStarterWeb)
	implementation(libs.flywayDatabasePostgresql)
	implementation(libs.jacksonDatabindNullable)
	implementation(libs.mapstruct)
	implementation(libs.springdocOpenapi)
	implementation(libs.spotlessLib)
	implementation(libs.spotlessPluginGradle)
	runtimeOnly(libs.postgresql)
	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)
	annotationProcessor(libs.mapstructProcessor)
	testImplementation(libs.springBootStarterTest)
	testImplementation(libs.testcontainersPostgresql)
	testImplementation(libs.restAssured)
	testRuntimeOnly(libs.junitPlatformLauncher)
}

spotless {
	java {
		target("src/*/java/**/*.java")
		toggleOffOn()
		palantirJavaFormat()
		removeUnusedImports()
		trimTrailingWhitespace()
		endWithNewline()
		formatAnnotations()
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
