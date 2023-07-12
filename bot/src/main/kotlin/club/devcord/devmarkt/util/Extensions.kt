package club.devcord.devmarkt.util

import io.ktor.client.statement.*

val HttpResponse.responseMillis: Long
	get() = responseTime.timestamp - requestTime.timestamp
