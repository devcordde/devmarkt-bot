package club.devcord.devmarkt

import club.devcord.devmarkt.backend.requests.checkBackendHealth
import club.devcord.devmarkt.config.BotConfig
import club.devcord.devmarkt.discord.commands.HealthCheckCommand
import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.expediagroup.graphql.client.serialization.GraphQLClientKotlinxSerializer
import com.kotlindiscord.kord.extensions.ExtensibleBot
import dev.kord.core.Kord
import dev.kord.core.entity.effectiveName
import dev.kord.core.kordLogger
import dev.kord.core.supplier.EntitySupplyStrategy
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.count
import java.net.URL
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

object DevmarktBot : CoroutineScope {

	val kord: Kord
		get() = kordexClient.kordRef

	lateinit var kordexClient: ExtensibleBot

	lateinit var graphQlClient: GraphQLKtorClient

	var httpClient: HttpClient = HttpClient(CIO) {
		install(ContentNegotiation) {
			json()
		}
	}

	suspend fun startBot() = coroutineScope {
		httpClient.checkBackendHealth { isUp, _ ->
			if (isUp) return@checkBackendHealth

			kordLogger.info {
				"Backend is not reachable or sends invalid data. Please check the backend url (DEVMARKT_BACKEND_URL=<backend_url>) or if the backend is currently running and is well configured."
			}

			launch {
				if (this@DevmarktBot::graphQlClient.isInitialized) graphQlClient.close()
				if (this@DevmarktBot::graphQlClient.isInitialized) httpClient.close()
				if (this@DevmarktBot::graphQlClient.isInitialized) kord.shutdown()
			}

			exitProcess(1)
		}.await()

		graphQlClient = GraphQLKtorClient(
			URL("http://${BotConfig.BACKEND_URL}/graphql"),
			httpClient = httpClient,
			serializer = GraphQLClientKotlinxSerializer()
		)

		kordexClient = ExtensibleBot(BotConfig.BOT_TOKEN) {
			this.applicationCommands {
				enabled = true
			}

			extensions {
				add { HealthCheckCommand("health-check") }
			}
		}
		kordLogger.info(
			"Starting devmarkt bot. [ID: {}, Username: {}]",
			kord.selfId,
			kord.getSelf(EntitySupplyStrategy.cacheWithCachingRestFallback).effectiveName
		)

		kordexClient.start()

		kordLogger.info {
			launch {
				"Currently on ${kord.guilds.count()} guilds."
			}
		}

	}

	private val job: Job = Job()

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.Default + job
}

suspend fun main() {
	DevmarktBot.startBot()
}

