package club.devcord.devmarkt.discord.commands

import club.devcord.devmarkt.DevmarktBot
import club.devcord.devmarkt.backend.requests.checkBackendHealth
import club.devcord.devmarkt.util.guildIcon
import com.kotlindiscord.kord.extensions.DISCORD_GREEN
import com.kotlindiscord.kord.extensions.DISCORD_RED
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.ephemeralSlashCommand
import dev.kord.common.entity.Permission
import dev.kord.rest.builder.message.embed

class HealthCheckCommand(override val name: String) : Extension() {

	override suspend fun setup() {
		ephemeralSlashCommand {
			name = "health"
			description = "Checks if the bot can build a connection to the backend. This command has a cool down of 1 minute."

			requirePermission(
				Permission.Administrator
			)

			action {
				val client = DevmarktBot.httpClient

				val guild = (member ?: return@action).guild

				respond {
					client.checkBackendHealth { isUp, millis ->
						embed {
							title = if (isUp) "Backend accessible ✅" else "Backend unreachable ❌"
							color = if (isUp) DISCORD_GREEN else DISCORD_RED

							thumbnail {
								guildIcon(guild)
							}

							field {
								name = if (isUp) "⏳" else "❌"
								value = if (isUp)
									"The backend is online and is accessible by the client. It took the ${millis}ms to respond."
								else "The backend is not reachable."
							}
						}
					}
				}
			}
		}
	}
}
