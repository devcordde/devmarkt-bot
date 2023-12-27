package club.devcord.devmarkt.discord.events

import club.devcord.devmarkt.DevmarktBot
import club.devcord.devmarkt.config.BotConfig
import dev.kord.common.entity.ButtonStyle
import dev.kord.core.behavior.edit
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.message.actionRow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach


val updatePostButton = DevmarktBot.kord.on<MessageCreateEvent> {
	if (message.channelId.value != BotConfig.DEVMARKT_CHANNEL.toULong()) return@on
	val author = message.author ?: return@on
	if (kord.selfId != author.id) return@on

	message.channel.messages.onEach { message ->
		message.edit { actionRow {  }}
	}.last().edit {
		actionRow {
			interactionButton(ButtonStyle.Secondary, "devmarkt:create-post") {
				label = "Click misch"
			}
		}
	}
}