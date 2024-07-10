package club.devcord.devmarkt.discord.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CreateCreationMessage extends ListenerAdapter {

  @Override
  public void onMessageReceived(@NotNull MessageReceivedEvent event) {
    if (event.getMember() == null) {
      return;
    }

    if (!event.getMember().getUser().isBot()) return;

    if (event.getMessage().getEmbeds().isEmpty()) return;

    event.getChannel().getHistoryBefore(event.getMessage(), 1).queue(history ->
        history.getRetrievedHistory().getFirst().delete().queue());

    event.getChannel().sendMessageEmbeds(new EmbedBuilder()
        .setColor(Color.ORANGE)
        .addField("\uD83D\uDCB8", "Press the button below to create a request.", true)
        .build()
    ).queue();
  }
}
