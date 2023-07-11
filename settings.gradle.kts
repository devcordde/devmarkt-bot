rootProject.name = "devmarkt-bot"

dependencyResolutionManagement {

	versionCatalogs {
		create("libs") {

			version("kotlin", "1.9.0")
			version("kotlin-serialization", "1.5.1")

			plugin("jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
			plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin-serialization")

			version("ktor", "2.3.2")

			library("ktor-client-core", "io.ktor", "ktor-client-core").versionRef("ktor")
			library("ktor-client-cio", "io.ktor", "ktor-client-cio").versionRef("ktor")
			library("ktor-client-negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
			library("ktor-serialization-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

			version("kgraphql", "0.19.0")

			library("kgraphql", "com.apurebase", "kgraphql").versionRef("kgraphql")
			library("kgraphql-ktor", "com.apurebase", "kgraphql-ktor").versionRef("kgraphql")

		}
	}
}