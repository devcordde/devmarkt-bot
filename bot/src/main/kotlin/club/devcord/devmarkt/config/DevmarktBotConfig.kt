package club.devcord.devmarkt.config

import com.kotlindiscord.kord.extensions.utils.envOrNull

object DevmarktBotConfig {

	val BOT_TOKEN: String
		get() = envOrNull("DEVMARKT_BOT_TOKEN")
			?: throw NullPointerException("Bot token not found. DEVMARKT_BOT_TOKEN=<bot_token>")

	val JWT_TOKEN: String
		get() = envOrNull("DEVMARKT_BOT_JWT_TOKEN")
			?: throw NullPointerException("Bot token not found. DEVMARKT_BOT_JWT_TOKEN=<jwt_token>")

	val BACKEND_HOST = envOrNull("DEVMARKT_BACKEND_HOST")
		?: throw NullPointerException("Backend host not found. DEVMARKT_BACKEND_HOST=<backend_host>")

	val BACKEND_PORT = envOrNull("DEVMARKT_BACKEND_PORT")
		?: throw NullPointerException("Backend port not found. DEVMARKT_BACKEND_PORT=<backend_port>")

	val BACKEND_URL = "$BACKEND_HOST:$BACKEND_PORT"
}