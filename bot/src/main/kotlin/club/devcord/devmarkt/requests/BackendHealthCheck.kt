package club.devcord.devmarkt.requests

import club.devcord.devmarkt.DevmarktBot
import club.devcord.devmarkt.config.DevmarktBotConfig
import dev.kord.core.kordLogger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class Health(
	val status: Status
) {

	enum class Status {
		UP
	}
}

suspend fun HttpClient.checkBackendHealth(onFailure: () -> Unit) {
	val response = (this.get {
		url {
			protocol = URLProtocol.HTTP
			host = DevmarktBotConfig.BACKEND_HOST
			port = DevmarktBotConfig.BACKEND_PORT.toInt()
			path("/health")
		}
	})

	val isUp = (response.body() as Health).status == Health.Status.UP

	if(!isUp) onFailure()


	kordLogger.info {
		"${response.request.url} is up."
	}
}