package club.devcord.devmarkt.util

import club.devcord.devmarkt.DevmarktBot
import dev.kord.core.behavior.GuildBehavior
import dev.kord.rest.builder.message.EmbedBuilder
import io.ktor.client.statement.*
import kotlinx.coroutines.launch

val HttpResponse.responseMillis: Long
	get() = responseTime.timestamp - requestTime.timestamp

suspend fun GuildBehavior.guildLogoUrl() = fetchGuildOrNull()?.icon?.cdnUrl?.toUrl()

fun EmbedBuilder.Thumbnail.guildIcon(guildBehavior: GuildBehavior) {
	DevmarktBot.botScope.launch {
		guildBehavior.guildLogoUrl()?.let {
			url = it
		}
	}
}
