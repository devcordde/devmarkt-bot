package club.devcord.devmarkt

import club.devcord.devmarkt.config.DevmarktBotConfig
import club.devcord.devmarkt.requests.checkBackendHealth
import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.kotlindiscord.kord.extensions.ExtensibleBot
import dev.kord.core.Kord
import dev.kord.core.kordLogger
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import java.net.URL
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

object DevmarktBot : CoroutineScope {

	lateinit var kord: Kord
	lateinit var kordexClient: ExtensibleBot

	var graphQlClient: GraphQLKtorClient = GraphQLKtorClient(URL("http://${DevmarktBotConfig.BACKEND_URL}/graphql"))

	var httpClient: HttpClient = HttpClient(CIO) {
		install(ContentNegotiation) {
			json()
		}
	}

	suspend fun startBot() {
		httpClient.checkBackendHealth {
			kordLogger.info {
				"Backend is not reachable or sends invalid data. Please check the backend url (DEVMARKT_BACKEND_URL=<backend_url>) or if the backend is currently running and is well configured."
			}

			runBlocking {
				graphQlClient.close()
				httpClient.close()
				kord.shutdown()
			}

			exitProcess(1)
		}
		kordexClient = ExtensibleBot(DevmarktBotConfig.BOT_TOKEN) {
			chatCommands {
				enabled = false
			}

			this.applicationCommands {
				enabled = true
			}

			extensions {

			}
		}
		kord = kordexClient.kordRef
		kordLogger.info("Starting devmarkt bot. [ID: {}, Username: {}]", kord.selfId, kord.getSelf().globalName)
		kordexClient.start()
	}

	private val job: Job = Job()

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.Default + job
}

suspend fun main() {
	DevmarktBot.startBot()
}

