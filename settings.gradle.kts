rootProject.name = "devmarkt-bot"

include("bot")

dependencyResolutionManagement {

	versionCatalogs {
		create("libs") {

			version("kotlin", "1.9.21")

			plugin("jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
			plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")

			version("ktor", "2.3.7")
			
			plugin("ktor", "io.ktor.plugin").versionRef("ktor")

			library("ktor-client-core", "io.ktor", "ktor-client-core").withoutVersion()
			library("ktor-client-cio", "io.ktor", "ktor-client-cio").withoutVersion()
			library("ktor-client-resources", "io.ktor", "ktor-client-resources").withoutVersion()
			library("ktor-client-negotiation", "io.ktor", "ktor-client-content-negotiation").withoutVersion()
			library("ktor-serialization-json", "io.ktor", "ktor-serialization-kotlinx-json").withoutVersion()

			version("graphql", "7.0.2")

			library("graphql", "com.expediagroup", "graphql-kotlin-ktor-client").versionRef("graphql")

			version("kord", "0.12.0")

			library("kord-core", "dev.kord", "kord-core").versionRef("kord")
			library("kord-core-voice", "dev.kord", "kord-core-voice").versionRef("kord")
			library("kord-common", "dev.kord", "kord-common").versionRef("kord")

			library("kordex", "com.kotlindiscord.kord.extensions", "kord-extensions").version("1.6.0")

			library("logback-classic", "ch.qos.logback", "logback-classic").version("1.4.14")

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
					"logback-classic"
				)
			)
		}
	}
}