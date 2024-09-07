package club.devcord.devmarkt.discord.events;

import club.devcord.devmarkt.discord.request.CreateRequestInstruction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Objects;

public class ClickRequestButtonEvent extends ListenerAdapter {

  private static final Logger log = LoggerFactory.getLogger(ClickRequestButtonEvent.class);

  @Override
  public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
    if (!Objects.equals(event.getButton().getId(), "create_request")) return;

    log.info(event.getMessageId());

    var embed = new EmbedBuilder()
        .setColor(Color.ORANGE)
        .addField(new MessageEmbed.Field("Geld", "gebt geld", true))
        .build();

    CreateRequestInstruction.sendEmbed(event.getMessageChannel(), event.getMessageChannel().sendMessageEmbeds(embed))
        .thenCompose((a) -> event.reply("Post created!").setEphemeral(true).submit())
        .join();
  }
}
