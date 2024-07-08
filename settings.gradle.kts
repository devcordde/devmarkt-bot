pluginManagement {
	plugins {
		kotlin("jvm") version "2.0.0"
	}
}
rootProject.name = "devmarkt-bot"

include("bot")

dependencyResolutionManagement {

	versionCatalogs {
		create("libs") {
			library("jda", "net.dv8tion", "JDA").version("5.0.0-beta.24")
			library("cjda", "de.chojo", "cjda-util").version("2.9.5+beta.19")

			bundle(
				"discord", listOf(
					"jda", "cjda"
				)
			)

			library("dotenv", "io.github.cdimascio", "dotenv-java").version("3.0.0")

			library("jackson", "com.fasterxml.jackson.core", "jackson-databind").version("2.17.2")
		}
	}
}