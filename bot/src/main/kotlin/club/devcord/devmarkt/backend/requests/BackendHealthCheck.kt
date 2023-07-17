package club.devcord.devmarkt.backend.requests

import club.devcord.devmarkt.config.BotConfig
import club.devcord.devmarkt.util.responseMillis
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

suspend fun HttpClient.checkBackendHealth(handle: (Boolean, Long?) -> Unit): Unit {
	return coroutineScope {
		async {
			val response = try {
				(this@checkBackendHealth.get {
					url {
						protocol = URLProtocol.HTTP
						host = BotConfig.BACKEND_HOST
						port = BotConfig.BACKEND_PORT.toInt()
						path("/health")
					}
				})
			} catch (_: ConnectException) {
				handle(false, null)
				return@async
			}

			val isUp = (response.body() as Health).status == Health.Status.UP

			val millis = if (isUp) response.responseMillis else null

			handle(isUp, millis)
		}.await()
	}
}