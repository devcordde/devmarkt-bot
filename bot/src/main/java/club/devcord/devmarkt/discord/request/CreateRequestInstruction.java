package club.devcord.devmarkt.discord.request;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class CreateRequestInstruction {

  private static final Logger log = LoggerFactory.getLogger(CreateRequestInstruction.class);

  public static CompletableFuture<Void> sendEmbed(MessageChannel channel, MessageCreateAction between) {
    var embed = new EmbedBuilder()
        .setColor(Color.ORANGE)
        .addField("Devmarkt \uD83D\uDCB8", "Press the button below to create a request.", true)
        .build();

    var sendCreationEmbed = channel.sendMessageEmbeds(embed)
        .addActionRow(Button.success("create_request", "\uD83D\uDCB8"));

    return channel.getHistory().retrievePast(1).submit()
        .thenApply(message -> message.getFirst().delete().submit())
        .thenAccept((a) -> {
          if (between != null) between.queue();
          if (between != null) {
            between.queue(sent -> sendCreationEmbed.queue());
            return;
          }

          sendCreationEmbed.queue();
        });
  }
}
