plugins {
	alias(libs.plugins.jvm)
	alias(libs.plugins.serialization)
}

repositories {
	mavenCentral()
	maven("https://s01.oss.sonatype.org/content/repositories/snapshots") //kordex
}

dependencies {
	implementation(libs.bundles.ktor)
	implementation(libs.graphql)
	implementation(libs.bundles.logging)
	implementation(libs.bundles.kord)
}