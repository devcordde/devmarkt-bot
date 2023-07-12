package club.devcord.devmarkt.backend.requests

import club.devcord.devmarkt.config.DevmarktBotConfig
import club.devcord.devmarkt.util.responseMillis
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import java.net.ConnectException

@Serializable
data class Health(
	val status: Status
) {

	enum class Status {
		UP
	}
}

suspend fun HttpClient.checkBackendHealth(handle: (Boolean, Long?) -> Unit) {
	val response = try {
		(this.get {
			url {
				protocol = URLProtocol.HTTP
				host = DevmarktBotConfig.BACKEND_HOST
				port = DevmarktBotConfig.BACKEND_PORT.toInt()
				path("/health")
			}
		})
	} catch (_: ConnectException) {
		handle(false, null)
		return
	}

	val isUp = (response.body() as Health).status == Health.Status.UP

	val millis = if (isUp) response.responseMillis else null

	handle(isUp, millis)
}