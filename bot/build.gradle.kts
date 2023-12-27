import com.google.cloud.tools.jib.plugins.common.BuildStepsExecutionException
import io.ktor.plugin.features.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	alias(libs.plugins.jvm)
	alias(libs.plugins.ktor)
	alias(libs.plugins.serialization)
}

version = "1.0"
group = "club.devcord"

val javaVersion = JavaVersion.VERSION_21

repositories {
	mavenCentral()
	maven {
		name = "Sonatype Snapshots (Legacy)"
		url = uri("https://oss.sonatype.org/content/repositories/snapshots")
	}

	maven {
		name = "Sonatype Snapshots"
		url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
	}
}

dependencies {
	implementation(libs.bundles.logging)
	implementation(libs.bundles.ktor)
	implementation(libs.bundles.kord)
	implementation(libs.graphql)
}

tasks {

	application {
		mainClass = "club.devcord.devmarkt.DevmarktBot"
	}

	ktor {
		fatJar {
			archiveFileName = "${project.name}-$version-fat.jar"
		}

		docker {
			localImageName = "devmarkt-bot"
			imageTag = version.toString()
			jreVersion = javaVersion

			portMappings = emptyList()

			externalRegistry = DockerImageRegistry.externalRegistry(
				project = provider { "bot" },
				username = providers.environmentVariable("REGISTRY_USERNAME"),
				password = providers.environmentVariable("REGISTRY_PASSWORD"),
				hostname = providers.environmentVariable("REGISTRY_HOSTNAME"),
				namespace = providers.environmentVariable("REGISTRY_NAMESPACE")
			)
		}
	}

	withType<JavaCompile> {
		options.encoding = "UTF-8"
		options.release = javaVersion.majorVersion.toInt()
	}

	withType<KotlinCompile> {
		kotlinOptions.jvmTarget = javaVersion.majorVersion
	}
}