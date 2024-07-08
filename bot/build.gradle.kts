plugins {
	java
	application
	kotlin("jvm")
}

version = "1.0"
group = "club.devcord"

repositories {
	mavenCentral()
	maven("https://eldonexus.de/repository/maven-public")
}

dependencies {
	implementation(libs.dotenv)
	implementation(libs.jackson)
	implementation(libs.bundles.discord)
	implementation(kotlin("stdlib-jdk8"))
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}