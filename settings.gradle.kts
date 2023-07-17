rootProject.name = "devmarkt-bot"

include("bot")

dependencyResolutionManagement {

	versionCatalogs {
		create("libs") {

			version("kotlin", "1.9.0")

			plugin("jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
			plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")

			version("ktor", "2.3.2")

			library("ktor-client-core", "io.ktor", "ktor-client-core").versionRef("ktor")
			library("ktor-client-cio", "io.ktor", "ktor-client-cio").versionRef("ktor")
			library("ktor-client-resources", "io.ktor", "ktor-client-resources").versionRef("ktor")
			library("ktor-client-negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
			library("ktor-serialization-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

			version("graphql", "6.5.3")

			library("graphql", "com.expediagroup", "graphql-kotlin-ktor-client").versionRef("graphql")

			version("kord", "0.10.0")

			library("kord-core", "dev.kord", "kord-core").versionRef("kord")
			library("kord-core-voice", "dev.kord", "kord-core-voice").versionRef("kord")
			library("kord-common", "dev.kord", "kord-common").versionRef("kord")

			library("kordex", "com.kotlindiscord.kord.extensions", "kord-extensions").version("1.5.8-SNAPSHOT")

			library("kotlin-logging", "io.github.microutils", "kotlin-logging-jvm").version("3.0.5")
			library("logback-classic", "ch.qos.logback", "logback-classic").version("1.4.8")

			bundle(
				"ktor", listOf(
					"ktor-client-core",
					"ktor-client-cio",
					"ktor-client-resources",
					"ktor-client-negotiation",
					"ktor-serialization-json"
				)
			)

			bundle(
				"kord", listOf(
					"kord-core",
					"kord-core-voice",
					"kord-common",
					"kordex"
				)
			)

			bundle(
				"logging", listOf(
					"kotlin-logging",
					"logback-classic"
				)
			)
		}
	}
}