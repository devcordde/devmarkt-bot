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
			library("jda", "net.dv8tion", "JDA").version("5.1.0")
			library("cjda", "de.chojo", "cjda-util").version("2.10.1+jda-5.1.0")

			bundle(
				"discord", listOf(
					"jda", "cjda"
				)
			)

			library("jackson", "com.fasterxml.jackson.core", "jackson-databind").version("2.17.2")

			library("logback", "ch.qos.logback", "logback-classic").version("1.5.6")
		}
	}
}