plugins {
	java
	application
}

version = "1.0"
group = "club.devcord"

repositories {
	mavenCentral()
	maven("https://eldonexus.de/repository/maven-public")
}

dependencies {
	implementation(libs.dotenv)
	implementation(libs.bundles.discord)
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}